package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.entity.InventarioCarte;
import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.util.TsmDbUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAcquistiService {

    private final AcquistiCarteRepo acquistiCarteRepo;
    private final InventarioCarteRepo inventarioCarteRepo;
    private final TsmDbUtils tsmDbUtils;

    @Transactional
    public CarteAcquisto addAcquistoCarte(AddAcquistoCarteRequest request, HttpHeaders header){
        var requestId = (!ObjectUtils.isEmpty(header.getFirst("requestId"))) ? header.getFirst("requestId") : UUID.randomUUID().toString();
        log.info("AddAcquistoCarte service started with requestId: {} , and raw request: {}",requestId,request);
        // mappo request su entity
        var entity =(CarteAcquisto) tsmDbUtils.mapperEntity(request,CarteAcquisto.class);
        entity.setDataAcquisto(request.dataAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // deduco costo totale
        var costoTotale = (request.quantitaAcquistata() > 1) ? (request.quantitaAcquistata() * request.prezzoAcquisto()) : request.prezzoAcquisto();
        entity.setCostoTotaleAcquisto(costoTotale);
        // creo id univoco dell'acquisto
        var acquistoTrxId = String.valueOf((request.nomeAcquisto() + request.dataAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).hashCode());
        // addo trxId acquisto
        entity.setCodiceAcquisto(acquistoTrxId);

        try(var scope =  Executors.newVirtualThreadPerTaskExecutor()) {
            // chiamata su thread virtuale per add inventario
            scope.execute(() -> addInventario(requestId, request.nomeAcquisto(), acquistoTrxId, request.quantitaAcquistata()));
            // chiamata a add acquisto carte repo
            var resp = scope.submit(() -> acquistiCarteRepo.save(entity)).get();
            log.info("AddAcquistoCarte service ended successfully for requestId: {}", resp);
            return resp;

        }catch (Exception e){
            log.error("Error on addAcquistoCarte service with err: {}",e.getMessage());
            throw new TsmDbException("Error on addAcquistoCarte service","05");
        }
    }


    private void addInventario(String requestId, String nomeAcquisto,String codiceAcquisto, Integer quantitaAcquistata){
        log.info("Addinventario started for requestId : {}",requestId);

        // controllo se gia esiste un invetario per questo articolo in base al nome
         var inventarioExist = inventarioCarteRepo.findByNomeAcquisto(nomeAcquisto);
         // caso gia esiste, aumento quantita disponibile e addo codice acquisto
         if(inventarioExist.isPresent()) {
             inventarioExist.get().getCodiciAcquisti().add(codiceAcquisto);
             var quantita = inventarioExist.get().getQuantitaDisponibile() + quantitaAcquistata;
             inventarioExist.get().setQuantitaDisponibile(quantita);
             inventarioCarteRepo.save(inventarioExist.get());
         }
         // caso primo add
         else{
             var inventarioFist = new InventarioCarte();
             inventarioFist.setCodiciAcquisti(new ArrayList<>());
             inventarioFist.getCodiciAcquisti().add(codiceAcquisto);
             inventarioFist.setNomeAcquisto(nomeAcquisto);
             inventarioFist.setQuantitaDisponibile(quantitaAcquistata);
             inventarioCarteRepo.save(inventarioFist);
         }
         log.info("AddInventario ended succesfully for requestId: {}",requestId);
    }
}
