package com.tsm.resell.world.db.model.request.vendite;

import java.time.LocalDateTime;

public record AddVenditeCarteRequest(String nomeCarte, String tcgCardGame, String condizioniCarta, String espansione, LocalDateTime dataVendita,
                                     Double prezzoVendita, Integer quantitaVendita, String piattaformaVendita, String codice,
                                     Double speseVendita, String chiaveAcqusito, Boolean setSpeciale) {

    //ritorna true se non e valido
    public Boolean validateUpdateVenditaRequest(){
        return nomeCarte == null &&
                tcgCardGame == null &&
                condizioniCarta == null &&
                espansione == null &&
                dataVendita == null &&
                prezzoVendita == null &&
                quantitaVendita == null &&
                piattaformaVendita == null &&
                codice == null &&
                speseVendita == null &&
                chiaveAcqusito == null;
    }
}
