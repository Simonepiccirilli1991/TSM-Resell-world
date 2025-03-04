package com.tsm.resell.world.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "invetario_carte")
public class InventarioCarte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "nomeAcquisto",unique = true,nullable = false)
    private String nomeAcquisto;
    @Column(name = "quantita_disponibile")
    private Integer quantitaDisponibile;
    @Column(name = "quantita_vendute")
    private Integer quantitaVendute;
    private List<String> codiciAcquisti;
    private List<String> codiciVendite;
}
