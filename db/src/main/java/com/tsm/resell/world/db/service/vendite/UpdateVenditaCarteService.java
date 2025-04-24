package com.tsm.resell.world.db.service.vendite;


import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.request.vendite.UpdateVenditaCarteRequest;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateVenditaCarteService {


    private final InventarioCarteRepo inventarioCarteRepo;
    private final VenditaCarteRepo venditaCarteRepo;


    @Transactional
    public CarteVendita updateCarteVendita(UpdateVenditaCarteRequest request, HttpHeaders headers){

        var tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId"))) ? headers.getFirst("tracingId") : UUID.randomUUID().toString();

        log.info("UpdateCarteVendita service started with raw request: {} , and TracingId: {}",request,tracingId);

        //controllo se la request e valida, se non e valida il metodo torna true
        if(Boolean.TRUE.equals(request.update().validateUpdateVenditaRequest())){
            log.error("Error on UpdateCarteVendita, invalid request provided, TracingId: {}",tracingId);
            throw new TsmDbException("Error on UpdateCarteVendita, invalid request provided","02");
        }

        // recupero entity originale
        var entity = venditaCarteRepo.findByCodiceVendita(request.codiceVendita())
                .orElseThrow(() -> {
                    log.error("Error on UpdateCarteVendita , codiceVendita non valido entity not found with TracingId: {}",tracingId);
                    return new TsmDbException("Error on UpdateCarteVendita , codiceVendita non valido entity","03");
                });

        // controllo se il nome e cambiato
        if(!entity.getNomeCarte().equals(request.update().nomeCarte())){
            //TODO: creare metodo che gestisce il cambio nome e relativo rollback
        }

        // controllo se e cambiato il prezzo o la spesa o la quantità e relativa gestione inventario
        if(entity.getPrezzoVendita() != request.update().prezzoVendita() || entity.getQuantitaVendita() != request.update().quantitaVendita()
        || entity.getSpeseVendita() != request.update().speseVendita()){
            //TODO: creare metodo che gestisce caso duso e relativo rollback
        }

        // gestisco caso update parametri restati
        //TODO: creare metodo che gestisce update parametri restati se presenti

        // salvo update
        var resp = venditaCarteRepo.save(entity);
        log.info("UpdateCarteVendita service ended successfully with tracingId: {}",tracingId);
        return resp;
    }

    private void gestioneUpdateNomeCarta(){

    }

    private void gestioUpdateCosti(){

    }

    private void gestioneUpdateParametri(){

    }
}
