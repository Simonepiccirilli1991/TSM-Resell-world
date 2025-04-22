package com.tsm.resell.world.db.service.vendite;


import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateVenditaCarteService {


    private final InventarioCarteRepo inventarioCarteRepo;
    private final VenditaCarteRepo venditaCarteRepo;


    @Transactional
    public CarteVendita updateCarteVendita(){

        //TODO: creare update carta vendita + business logic e relative get + test

    }
}
