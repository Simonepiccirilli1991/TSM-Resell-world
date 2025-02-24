package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquistiCarteRepo extends JpaRepository<CarteAcquisto,Integer> {
}
