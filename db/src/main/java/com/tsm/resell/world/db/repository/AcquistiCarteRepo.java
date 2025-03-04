package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AcquistiCarteRepo extends JpaRepository<CarteAcquisto,Integer> {


    Optional<CarteAcquisto> findByCodiceAcquisto(String codiceAcquisto);

    @Query(value = "SELECT c FROM carteAcquisto c WHERE c.nomeAcquisto = :nomeAcquisto",nativeQuery = true)
    List<CarteAcquisto> findByNomeAcquisto(@Param("nomeAcquisto") String nomeAcquisto);

    @Query(value = "SELECT c FROM carteAcquisto c WHERE c.prezzoAcquisto >= : prezzoBase AND WHERE c.prezzoAcquisto <= : prezzoMax",nativeQuery = true)
    List<CarteAcquisto> filterForPrezzoAcquisto(@Param("prezzoBase") Double prezzoBase,
                                                @Param("prezzoMax") Double prezzoMax);
}
