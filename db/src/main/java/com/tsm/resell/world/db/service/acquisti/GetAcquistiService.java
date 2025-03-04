package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.vendite.GetAcquistiResponse;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAcquistiService {

    private final AcquistiCarteRepo acquistiCarteRepo;


    public GetAcquistiResponse getCarteAcquisto(GetAcquistiRequest request){
        log.info("GetCarteAcquisto service started with request: {}",request);

        var resp = switch (request){

            case GetAcquistiRequest i when !ObjectUtils.isEmpty(i.codiceAcquisto()) -> {
                log.info("Filtering for codice acquisto");
                var iResp = acquistiCarteRepo.findByCodiceAcquisto(i.codiceAcquisto());
                if(iResp.isPresent())
                    yield List.of(iResp.get());
                else
                    yield List.of();
            }

            case GetAcquistiRequest i when !ObjectUtils.isEmpty(i.nome()) -> {
                log.info("Filtering for nome");
                yield acquistiCarteRepo.findByNomeAcquisto(i.nome());
            }

            case GetAcquistiRequest i when !ObjectUtils.isEmpty(i.prezzoStart()) && !ObjectUtils.isEmpty(i.prezzoEnd()) -> {
                log.info("Filtering for price");
                yield acquistiCarteRepo.filterForPrezzoAcquisto(i.prezzoStart(),i.prezzoEnd());
            }

            default -> {
                log.info("Filterig not found, returning all");
                yield acquistiCarteRepo.findAll();
            }
        };

        log.info("GetCarteAcquisto with filtering ended successfully ");
        return new GetAcquistiResponse((List<CarteAcquisto>) resp);
    }


}
