package com.tsm.resell.world.db.service.vendite;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import com.tsm.resell.world.db.util.TsmDbUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.StructuredTaskScope;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddVenditaCarteService {


    private final VenditaCarteRepo venditaCarteRepo;
    private final InventarioCarteRepo inventarioCarteRepo;
    private final AcquistiCarteRepo acquistiCarteRepo;
    private final TsmDbUtils tsmDbUtils;
    private String tracingId;

    @Transactional
    public CarteVendita addCarteVendita(AddVenditeCarteRequest request, HttpHeaders headers){

        tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId"))) ? headers.getFirst("tracingId") : UUID.randomUUID().toString();
        log.info("AddCarteVendita service started with raw request: {} , tracingId: {}",request,tracingId);

        //controllo se la chiave di vendita esiste
        acquistiCarteRepo.findByCodiceAcquisto(request.chiaveAcqusito())
                .orElseThrow(() -> {
                    log.error("Error on addCarteVendita service, chiave acquisto not found - tracingId: {}",tracingId);
                    return new TsmDbException("Error on addCarteVednita, chiave acquisto non combacio","03");
                });

        // mappo request su dto
        var entityVendita = (CarteVendita) tsmDbUtils.mapperEntity(request, CarteVendita.class);
        // genero chiave vendita
        var codiceVendita = String.valueOf((request.nomeCarte() + request.dataVendita()).hashCode());
        entityVendita.setCodiceVendita(codiceVendita);

        // calcolo il totale
        var totale = (request.quantitaVendita() > 1) ? (request.quantitaVendita() * request.prezzoVendita()) : request.prezzoVendita();
        var netto = (!ObjectUtils.isEmpty(request.speseVendita())) ? totale - request.speseVendita() : totale;
        // setto su dtp
        entityVendita.setNettoVendita(netto);
        entityVendita.setPrezzoTotaleVendita(totale);

        // uso queste perche il thread virtuale non gestisce e non eredita la transazionalità dal thread principale
        // devo salvare entity e settare la parte inventario
        try{
            // chiamata per salvare a db
            var resp =  venditaCarteRepo.save(entityVendita);
            // chiamata per salvare inventario
            addVenditaInventario(codiceVendita,request.nomeCarte(), request.quantitaVendita());

            log.info("AddCarteVendita service ended successfully, tracingId: {}");
            return resp;

        }catch (Exception e){
            log.error("Error on addCarteVenditaService with error: {}",e.getMessage());
            // messo perche protrebbe rimanere appesso in thread in caso di eccezzione
            Thread.currentThread().interrupt();
            throw new TsmDbException("Error on addCarteVenditaService", "04");
        }
    }

    private void addVenditaInventario(String codiceVendita,String nomeAcquisto,Integer quantitaVenduta){

        var inventario = inventarioCarteRepo.findByNomeAcquisto(nomeAcquisto)
                .orElseThrow(() -> {
                    log.error("Error on addCarteVenditaService, oggetto non trovato in inventario - tracingId: {}",tracingId);
                    return new TsmDbException("Error on addCarteVenditaService, oggetto non matcha in inventario", "05");
                });
        // se e prima volta istanzio
        if(ObjectUtils.isEmpty(inventario.getCodiciAcquisti()))
            inventario.setCodiciAcquisti(new ArrayList<>());

        inventario.getCodiciAcquisti().add(codiceVendita);
        var updateDisponibile = inventario.getQuantitaDisponibile() - quantitaVenduta;
        inventario.setQuantitaDisponibile(updateDisponibile);

        inventarioCarteRepo.save(inventario);
    }
}
