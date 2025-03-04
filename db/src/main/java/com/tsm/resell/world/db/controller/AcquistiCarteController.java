package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.vendite.GetAcquistiResponse;
import com.tsm.resell.world.db.service.acquisti.AddAcquistiService;
import com.tsm.resell.world.db.service.acquisti.GetAcquistiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AcquistiCarteController {

    private final AddAcquistiService addAcquistiService;
    private final GetAcquistiService getAcquistiService;


    @PostMapping("addcarta")
    public Mono<ResponseEntity<CarteAcquisto>> addCartaAcquisto(@RequestBody AddAcquistoCarteRequest request, @RequestHeader HttpHeaders header){
        return Mono.just(ResponseEntity.ok(addAcquistiService.addAcquistoCarte(request,header)));
    }

    @PostMapping("getcarta")
    public Mono<ResponseEntity<GetAcquistiResponse>> getAcquistiResponse(@RequestBody GetAcquistiRequest request){
        return Mono.just(ResponseEntity.ok(getAcquistiService.getCarteAcquisto(request)));
    }
}
