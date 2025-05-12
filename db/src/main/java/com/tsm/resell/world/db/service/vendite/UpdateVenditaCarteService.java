package com.tsm.resell.world.db.service.vendite;


import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.model.request.vendite.UpdateVenditaCarteRequest;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
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

        // controllo se e cambiato il prezzo o la spesa o la quantità e relativa gestione inventario
        if(!entity.getPrezzoVendita().equals(request.update().prezzoVendita()) || !entity.getQuantitaVendita().equals(request.update().quantitaVendita())
        || !entity.getSpeseVendita().equals(request.update().speseVendita())){
            gestioUpdateCosti(entity,request.update().prezzoVendita(), request.update().quantitaVendita(),request.update().speseVendita(), tracingId);
        }

        // gestisco caso update parametri restati
        gestioneUpdateParametri(entity,request.update());

        // salvo update
        var resp = venditaCarteRepo.save(entity);
        log.info("UpdateCarteVendita service ended successfully with tracingId: {}",tracingId);
        return resp;
    }


    private void gestioUpdateCosti(CarteVendita entity,Double newPrezzoVendita, Integer newQuantitaVendita,Double newSpeseVendita, String tracingId){

        //mi prendo i dati originale
        var prezzoVenditaOriginale = entity.getPrezzoVendita();
        var quanitaVenditaOriginale = entity.getQuantitaVendita();
        var speseVenditaOriginale = entity.getSpeseVendita();

        // controllo prima il prezzo
        if(!prezzoVenditaOriginale.equals(newPrezzoVendita) && !ObjectUtils.isEmpty(newPrezzoVendita))
            entity.setPrezzoVendita(newPrezzoVendita);

        //controllo le spese
        if(!speseVenditaOriginale.equals(newSpeseVendita) && !ObjectUtils.isEmpty(newSpeseVendita))
            entity.setSpeseVendita(newSpeseVendita);

        // controllo quantita
        if(!quanitaVenditaOriginale.equals(newQuantitaVendita) && !ObjectUtils.isEmpty(newQuantitaVendita)) {
            entity.setQuantitaVendita(newQuantitaVendita);

            // devo aggiornare inventario
            var inventario = inventarioCarteRepo.findByNomeAcquisto(entity.getNomeCarte())
                    .orElseThrow(() -> {
                        log.error("Error on gestioneUpdateCosti, entity inventario non trovata with tracingId: {}",tracingId);
                        return new TsmDbException("Error on gestioneUpdateCosti, entity inventario not found","404");
                    });
            var quantitaVEndutaUpdate = (inventario.getQuantitaVendute() - quanitaVenditaOriginale) + newQuantitaVendita;
            var quantitaDisponibileUpdate = (inventario.getQuantitaDisponibile() + quanitaVenditaOriginale) - newQuantitaVendita;

            //updato inventario
            inventario.setQuantitaVendute(quantitaVEndutaUpdate);
            inventario.setQuantitaDisponibile(quantitaDisponibileUpdate);
            // salvo
            inventarioCarteRepo.save(inventario);
        }

        // ricalcolo il tutto sui dati aggiornati
        var nuovoPrezzoTotale = entity.getPrezzoVendita() * entity.getQuantitaVendita();
        var nuvoNettoVendita = nuovoPrezzoTotale - newSpeseVendita;

        entity.setPrezzoTotaleVendita(nuovoPrezzoTotale);
        entity.setNettoVendita(nuvoNettoVendita);
    }

    private void gestioneUpdateParametri(CarteVendita vendita, AddVenditeCarteRequest update){

        // devo gestire update dell'entity se sono valorizzati i dati di update come data , piattaforma, etc
        if(!ObjectUtils.isEmpty(update.dataVendita()))
            vendita.setDataVendita(update.dataVendita().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        // codice prodotto / carta, non la key
        if(!ObjectUtils.isEmpty(update.codice()))
            vendita.setCodice(update.codice());
        // condizioni carta
        if(!ObjectUtils.isEmpty(update.condizioniCarta()))
            vendita.setCondizioniCarta(update.condizioniCarta());
        // espansione
        if(!ObjectUtils.isEmpty(update.espansione()))
            vendita.setEspansione(update.espansione());
        // tcg card game
        if(!ObjectUtils.isEmpty(update.tcgCardGame()))
            vendita.setTcgCardGame(update.tcgCardGame());
        // piattaforma vendita
        if(!ObjectUtils.isEmpty(update.piattaformaVendita()))
            vendita.setPiattaformaVendita(update.piattaformaVendita());
    }
}
