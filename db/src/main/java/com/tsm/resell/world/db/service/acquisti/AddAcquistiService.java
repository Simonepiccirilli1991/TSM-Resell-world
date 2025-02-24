package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddAcquistiService {

    private final AcquistiCarteRepo acquistiCarteRepo;


    public CarteAcquisto addAcquistoCarte(){
        //TODO: implementare
    }
}
