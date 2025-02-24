package com.tsm.resell.world.db.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "invetarioCarte")
public class InventarioCarte {

    private Integer id;
    private String nomeAcquisto;
    private Integer quantitaDisponibile;
    private Integer quantitaVendute;
    private List<String> codiciAcquisti;
    private List<String> codiciVendite;
}
