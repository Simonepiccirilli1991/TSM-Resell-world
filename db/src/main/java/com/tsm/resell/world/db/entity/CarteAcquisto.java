package com.tsm.resell.world.db.entity;

import com.tsm.resell.world.db.entity.base.Acquisto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "carteAcquisto")
public class CarteAcquisto extends Acquisto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "espansione",nullable = false)
    private String espansione;
    @Column(name = "tcgCardGame",nullable = false)
    private String tcgCardGame;
    @Column(name = "condizioni",nullable = false)
    private String condizioni;
    @Column(name = "setSpeciale",nullable = false)
    private Boolean setSpeciale;
    @Column(name = "codiceProdotto",nullable = false)
    private String codiceProdotto;
    @Column(name = "codiceAcquisto",nullable = false,unique = true)
    private String codiceAcquisto;
}
