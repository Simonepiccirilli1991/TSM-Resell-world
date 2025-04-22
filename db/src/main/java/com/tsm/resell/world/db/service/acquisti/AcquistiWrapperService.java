package com.tsm.resell.world.db.service.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.acquisti.UpdateAcquistiCarteRequest;
import com.tsm.resell.world.db.model.response.BaseResponse;
import com.tsm.resell.world.db.model.response.acquisti.GetAcquistiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcquistiWrapperService {


    private final AddAcquistiService addAcquistiService;
    private final DeleteAcquistiService deleteAcquistiService;
    private final UpdateAcquistiService updateAcquistiService;
    private final GetAcquistiService getAcquistiService;



    public CarteAcquisto addAcquisto(AddAcquistoCarteRequest request, HttpHeaders headers){
        return addAcquistiService.addAcquistoCarte(request,headers);
    }


    public BaseResponse deleteAcquisto(String codiceAcquisto, HttpHeaders headers){
        return deleteAcquistiService.deleteAcquistoCarte(codiceAcquisto,headers);
    }


    public GetAcquistiResponse getAcquisti(GetAcquistiRequest request, HttpHeaders headers){
        return getAcquistiService.getCarteAcquisto(request, headers);
    }


    public CarteAcquisto updateAcquisto(UpdateAcquistiCarteRequest request, HttpHeaders headers){
        return updateAcquistiService.updateCarteAcquisto(request, headers);
    }

}
