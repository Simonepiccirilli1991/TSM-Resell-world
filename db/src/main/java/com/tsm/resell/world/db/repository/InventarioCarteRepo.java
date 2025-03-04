package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.InventarioCarte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioCarteRepo extends JpaRepository<InventarioCarte,Integer> {

    Optional<InventarioCarte> findByNomeAcquisto(String nomeAcquisto);

    @Query(value = "SELECT * FROM invetario_carte c WHERE c.quantita_disponibile = :quantitaDisponibile",nativeQuery = true)
    List<InventarioCarte> filterQuantitaDisponibile(@Param("quantitaDisponibile") Integer quantitaDisponibile);

    @Query(value = "SELECT * FROM inventario_carte c WHERE c.quantita_vendute = :quantitaVendute",nativeQuery = true)
    List<InventarioCarte> filterQuantitaVenduta(@Param("quantiaVendute") Integer quantitaVenduta);
}
