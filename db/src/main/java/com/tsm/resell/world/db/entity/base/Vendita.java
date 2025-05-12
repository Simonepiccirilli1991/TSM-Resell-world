package com.tsm.resell.world.db.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass //serve per dire a jpa che deve considerare e salvare i seguenti dati.
public abstract class Vendita {

    private String dataVendita;
    @Column(unique = true,nullable = false)
    private String codiceVendita;
    private Double prezzoVendita;
    private Integer quantitaVendita;
    private String piattaformaVendita;
}
