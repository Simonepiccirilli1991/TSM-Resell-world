package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AcquistiCarteRepo extends JpaRepository<CarteAcquisto,Integer> {


    Optional<CarteAcquisto> findByCodiceAcquisto(String codiceAcquisto);

    @Query(value = "SELECT * FROM carte_acquisto c WHERE c.nome_acquisto = :nomeAcquisto",nativeQuery = true)
    List<CarteAcquisto> findByNomeAcquisto(@Param("nomeAcquisto") String nomeAcquisto);

    @Query(value = "SELECT * FROM carte_acquisto c WHERE c.prezzo_acquisto >= :prezzoBase AND WHERE c.prezzoAcquisto <= :prezzoMax",nativeQuery = true)
    List<CarteAcquisto> filterForPrezzoAcquisto(@Param("prezzoBase") Double prezzoBase,
                                                @Param("prezzoMax") Double prezzoMax);
}
