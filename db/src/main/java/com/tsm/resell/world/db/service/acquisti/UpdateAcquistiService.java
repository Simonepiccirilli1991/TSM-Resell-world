package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.UpdateAcquistiCarteRequest;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import com.tsm.resell.world.db.util.TsmDbUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateAcquistiService {


    private final AcquistiCarteRepo acquistiCarteRepo;


    public CarteAcquisto updateCarteAcquisto(UpdateAcquistiCarteRequest request, HttpHeaders headers){

        var tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId"))) ? headers.getFirst("tracingId") : UUID.randomUUID();

        log.info("UpdateCarteAcquisto started with tracingId: {} , and request: {}",tracingId,request);

        // valido request, chiamo metodo di udpate, almeno 1 value deve essere presente
        if(Boolean.FALSE.equals(request.updateValues().validateUpdate())){
            log.error("Error on UpdateCarteAcquisto, invalid request with tracingId: {}",tracingId);
            throw new TsmDbException("Error on updateCarteAcquisto, invalid request provided","04");
        }

        // recupero entity da udpatare
        var entity = acquistiCarteRepo.findByCodiceAcquisto(request.codiceAcquisto())
                .orElseThrow(() -> {
                    log.error("Error on UpdateCarteAcquisto, carta non trovata con codice fornito with tracingId: {}",tracingId);
                    return new TsmDbException("Error on UopdateCartaACquisto , carta non trovata","03");
                });
        // metodo che compara i valori passati se diversi da nulle updata
        updateEntity(entity,request.updateValues());
        // salvo update
        var resp = acquistiCarteRepo.save(entity);
        log.info("UpdateCarteAcquisto ended successfully with tracingId: {}",tracingId);
        return resp;
    }


    // checka quale valori non sono nulle updata acquisto
    private void updateEntity(CarteAcquisto entity, AddAcquistoCarteRequest request) {
        if (request.nomeAcquisto() != null) entity.setNomeAcquisto(request.nomeAcquisto());
        if (request.dataAcquisto() != null) entity.setDataAcquisto(request.dataAcquisto().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (request.prezzoAcquisto() != null) entity.setPrezzoAcquisto(request.prezzoAcquisto());
        if (request.quantitaAcquistata() != null) entity.setQuantitaAcquistata(request.quantitaAcquistata());
        if (request.piattaformaAcquisto() != null) entity.setPiattaformaAcquisto(request.piattaformaAcquisto());
        if (request.espansione() != null) entity.setEspansione(request.espansione());
        if (request.tcgCardGame() != null) entity.setTcgCardGame(request.tcgCardGame());
        if (request.condizioni() != null) entity.setCondizioni(request.condizioni());
        if (request.setSpeciale() != null) entity.setSetSpeciale(request.setSpeciale());
        if (request.codiceProdotto() != null) entity.setCodiceProdotto(request.codiceProdotto());
    }
}
