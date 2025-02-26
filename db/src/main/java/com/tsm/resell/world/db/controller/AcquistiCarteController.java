package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.service.acquisti.AddAcquistiService;
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

    private AddAcquistiService addAcquistiService;


    @PostMapping("addcarta")
    public Mono<ResponseEntity<CarteAcquisto>> addCartaAcquisto(@RequestBody AddAcquistoCarteRequest request, @RequestHeader HttpHeaders header){
        return Mono.just(ResponseEntity.ok(addAcquistiService.addAcquistoCarte(request,header)));
    }
}
