package com.tsm.resell.world.db.entity.base;

import lombok.Data;

@Data
public abstract class Vendita {

    private String dataVendita;
    private String codiceVendita;
    private Double prezzoVendita;
    private Integer quantitaVendita;
    private String piattaformaVendita;
}
