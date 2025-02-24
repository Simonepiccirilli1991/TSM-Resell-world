package com.tsm.resell.world.db.entity.base;

import lombok.Data;

@Data
public abstract class Acquisto {

    private String nomeAcquisto;
    private String dataAcquisto;
    private Double prezzoAcquisto;
    private Integer quantitaAcquistata;
    private String piattaformaAcquisto;
    private Double costoTotaleAcquisto;
}
