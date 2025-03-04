package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.response.BaseResponse;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteAcquistiService {

    private final InventarioCarteRepo inventarioCarteRepo;
    private final AcquistiCarteRepo acquistiCarteRepo;


    @Transactional
    public BaseResponse deleteAcquistoCarte(String codiceAcquisto, HttpHeaders headers){

        var tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId")) ? headers.getFirst("tracingId") : UUID.randomUUID());
        log.info("DeleteAcquistiCarteService started for codiceAcquisto: {} , and tracingId: {}",codiceAcquisto,tracingId);
        // checko se entity esiste
        var entity = acquistiCarteRepo.findByCodiceAcquisto(codiceAcquisto)
                .orElseThrow( () -> {
                    log.error("Error on deleteAcquistiCarte service , missign entity");
                    return new TsmDbException("Missing entity","04");
                });
        // faccio in pallelo, ma non va di default su thread virtuale, imposto io abilitando i thrad virtuali su application yml
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            // deleto acquisto
            scope.fork(() -> {
                acquistiCarteRepo.delete(entity);
                return null;
            });
            // storno inventario
            scope.fork(() ->{
                aggiustaInventarioOnDelete(entity.getNomeAcquisto(),entity.getCodiceAcquisto(),entity.getQuantitaAcquistata());
                return null;
            });
            // joino e lancio eccezzione se 1 dei 2 fallisce con relativo rollback
            scope.join().throwIfFailed();
        }catch (Exception e){
            log.error("Error on deleteAcquistoCarte service with error: {}",e.getMessage());
            throw new TsmDbException("Error on deleteAcquistoCarta service with err: "+e.getMessage(),"03");
        }

        log.info("DeleteAcquistiCarteService ended successfully - tracingId {}",tracingId);
        return new BaseResponse("Delete successuglly","00");
    }


    private void aggiustaInventarioOnDelete(String nomeAcquisto,String codiceAcquisto, Integer quantitaStornare){

        var entity = inventarioCarteRepo.findByNomeAcquisto(nomeAcquisto)
                // non dovrebbe mai capitare , ma se non matchano lancio eccezzione
                .orElseThrow(() -> {
                    log.error("Error on DeleteAcquistiCarteService, missing inventario entity");
                    return new TsmDbException("Missing inventario entity","04");
                });
        // rimuovo codice acquisto da lsita
        entity.getCodiciAcquisti().remove(codiceAcquisto);

        var quantiaUpdate = entity.getQuantitaDisponibile() - quantitaStornare;
        entity.setQuantitaDisponibile(quantiaUpdate);

        inventarioCarteRepo.save(entity);
    }
}
