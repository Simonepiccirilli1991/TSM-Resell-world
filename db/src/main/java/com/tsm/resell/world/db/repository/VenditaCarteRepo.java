package com.tsm.resell.world.db.repository;

import com.tsm.resell.world.db.entity.CarteVendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenditaCarteRepo extends JpaRepository<CarteVendita,Integer> {

    Optional<CarteVendita> findByCodiceVendita(String codiceVendita);

    List<CarteVendita> findByNomeCarte(String nomeCarta);

    @Query(value = "SELECT * FROM carte_vendita i WHEN i.data_vendita >= :dataStart AND WHERE i.data_vendita <= :dataEnd",nativeQuery = true)
    List<CarteVendita> filterPerData(@Param("dataStart") String dataStart,
                                     @Param("dataEnd") String dataEnd);

    @Query(value = "SELECT * FROM carte_vendita i WHEN i.prezo_vendita >= :prezzoStart AND WHERE i.prezzo_vendita <= :prezzoEnd",nativeQuery = true)
    List<CarteVendita> filterPerPrezzoVendita(@Param("prezzoStart") Double prezzoStart,
                                              @Param("prezzoend") Double prezzoEnd);

    @Query(value = "SELECT * FROM carte_vendita i when i.quantita_venduta > :quantita",nativeQuery = true)
    List<CarteVendita> filtraPerQuantitaVendutaMaggioreDi(@Param("quantita") Integer quantita);
}
