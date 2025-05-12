package com.tsm.resell.world.db.service.vendite;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.model.request.vendite.GetVenditaCarteRequest;
import com.tsm.resell.world.db.model.request.vendite.UpdateVenditaCarteRequest;
import com.tsm.resell.world.db.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenditaWrapperService {

    private final AddVenditaCarteService addVenditaCarteService;
    private final DeleteVenditaCarteService deleteVenditaCarteService;
    private final UpdateVenditaCarteService updateVenditaCarteService;
    private final GetVenditeCarteService getVenditeCarteService;

    public CarteVendita addVenditaCarte(AddVenditeCarteRequest request, HttpHeaders headers){
        return addVenditaCarteService.addCarteVendita(request,headers);
    }

    public BaseResponse deleteCarteVendita(String codiceVendita,HttpHeaders headers){
        return deleteVenditaCarteService.deleteVendita(codiceVendita, headers);
    }

    public CarteVendita updateCarteVendita(UpdateVenditaCarteRequest request,HttpHeaders headers){
        return updateVenditaCarteService.updateCarteVendita(request,headers);
    }

    public List<CarteVendita> getCartevendita(GetVenditaCarteRequest request, HttpHeaders headers){
        return getVenditeCarteService.listaVendite(request,headers);
    }
}
