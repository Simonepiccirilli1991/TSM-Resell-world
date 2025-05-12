package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.model.request.vendite.UpdateVenditaCarteRequest;
import com.tsm.resell.world.db.model.response.BaseResponse;
import com.tsm.resell.world.db.service.vendite.VenditaWrapperService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/carte/vendite")
@RequiredArgsConstructor
public class VenditeCarteController {

    private final VenditaWrapperService venditaWrapperService;


    @PostMapping("add")
    public Mono<ResponseEntity<CarteVendita>> addCarteVEndita(@RequestBody AddVenditeCarteRequest request, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(venditaWrapperService.addVenditaCarte(request,headers)));
    }

    @DeleteMapping("delete")
    public Mono<ResponseEntity<BaseResponse>> deleteVenditaCarte(@PathParam("codiceVendita") String codiceVendita,@RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(venditaWrapperService.deleteCarteVendita(codiceVendita,headers)));
    }

    @PatchMapping("update")
    public Mono<ResponseEntity<CarteVendita>> updateVenditaCarte(@RequestBody UpdateVenditaCarteRequest request, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(venditaWrapperService.updateCarteVendita(request, headers)));
    }
}
