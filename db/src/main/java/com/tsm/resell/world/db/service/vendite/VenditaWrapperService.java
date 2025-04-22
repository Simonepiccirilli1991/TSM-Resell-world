package com.tsm.resell.world.db.service.vendite;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenditaWrapperService {

    private final AddVenditaCarteService addVenditaCarteService;
    private final DeleteVenditaCarteService deleteVenditaCarteService;


    public CarteVendita addVenditaCarte(AddVenditeCarteRequest request, HttpHeaders headers){
        return addVenditaCarteService.addCarteVendita(request,headers);
    }

    public BaseResponse deleteCarteVendita(String codiceVendita,HttpHeaders headers){
        return deleteVenditaCarteService.deleteVendita(codiceVendita, headers);
    }
}
