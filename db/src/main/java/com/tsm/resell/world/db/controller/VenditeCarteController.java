package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.service.vendite.AddVenditaCarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/carte/vendite")
@RequiredArgsConstructor
public class VenditeCarteController {

    private final AddVenditaCarteService addVenditaCarteService;


    public Mono<ResponseEntity<CarteVendita>> addCarteVEndita(@RequestBody AddVenditeCarteRequest request, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(addVenditaCarteService.addCarteVendita(request,headers)));
    }
}
