package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.CarteVendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaCarteRepo extends JpaRepository<CarteVendita,Integer> {
}
