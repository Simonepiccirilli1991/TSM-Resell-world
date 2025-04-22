package com.tsm.resell.world.db.entity.base;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public abstract class Vendita {

    private String dataVendita;
    @Column(unique = true,nullable = false)
    private String codiceVendita;
    private Double prezzoVendita;
    private Integer quantitaVendita;
    private String piattaformaVendita;
}
