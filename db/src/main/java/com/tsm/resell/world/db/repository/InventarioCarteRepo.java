package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.InventarioCarte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioCarteRepo extends JpaRepository<InventarioCarte,Integer> {

    Optional<InventarioCarte> findByNomeAcquisto(String nomeAcquisto);
}
