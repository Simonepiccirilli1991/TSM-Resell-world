package com.tsm.resell.world.db.model.request.vendite;

import java.time.LocalDateTime;

public record AddVenditeCarteRequest(String nomeCarte, String tcgCardGame, String condizioniCarta, String espansione, LocalDateTime dataVendita,
                                     Double prezzoVendita, Integer quantitaVendita, String piattaformaVendita, String codice,
                                     Double speseVendita, String chiaveAcqusito) {
}
