package com.tsm.resell.world.db.entity;

import com.tsm.resell.world.db.entity.base.Vendita;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "carteVemdita")
public class CarteVendita extends Vendita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "nomeCarta",nullable = false)
    private String nomeCarte;// va bene anche il nome prodotto
    @Column(name = "tcgCardGame",nullable = false)
    private String tcgCardGame;
    @Column(name = "condizioniCarta",nullable = false)
    private String condizioniCarta;
    @Column(name = "espansione",nullable = false)
    private String espansione;
    @Column(name = "setSpeciale",nullable = false)
    private Boolean setSpeciale;
    @Column(name = "codice",nullable = false)
    private String codice;
    @Column(name = "prezzoTotaleVendita",nullable = false)
    private Double prezzoTotaleVendita;
    @Column(name = "speseVendita",nullable = false)
    private Double speseVendita;
    @Column(name = "nettoVendita",nullable = false)
    private Double nettoVendita;
    @Column(name = "chiaveAcqusito",nullable = false)
    private String chiaveAcqusito;
}
