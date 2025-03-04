package com.tsm.resell.world.db.model.request.acquisti;

import java.time.LocalDateTime;

public record AddAcquistoCarteRequest(String nomeAcquisto, LocalDateTime dataAcquisto,Double prezzoAcquisto,
                                      Integer quantitaAcquistata, String piattaformaAcquisto, String espansione,
                                      String tcgCardGame, String condizioni, Boolean setSpeciale, String codiceProdotto) {
}
