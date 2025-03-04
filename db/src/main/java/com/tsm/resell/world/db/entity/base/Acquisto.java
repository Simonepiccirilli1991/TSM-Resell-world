package com.tsm.resell.world.db.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass //serve per dire a jpa che deve considerare e salvare i seguenti dati.
public abstract class Acquisto {

    @Column(name = "nome_acquisto",nullable = false)
    private String nomeAcquisto;
    private String dataAcquisto;
    @Column(name = "prezzo_acquisto",nullable = false)
    private Double prezzoAcquisto;
    private Integer quantitaAcquistata;
    private String piattaformaAcquisto;
    private Double costoTotaleAcquisto;
}
