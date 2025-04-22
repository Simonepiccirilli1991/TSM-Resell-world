package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.entity.CarteAcquisto;
import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.acquisti.UpdateAcquistiCarteRequest;
import com.tsm.resell.world.db.model.response.BaseResponse;
import com.tsm.resell.world.db.model.response.acquisti.GetAcquistiResponse;
import com.tsm.resell.world.db.service.acquisti.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AcquistiCarteController {

    private final AcquistiWrapperService acquistiWrapperService;


    @PostMapping("addcarta")
    public Mono<ResponseEntity<CarteAcquisto>> addCartaAcquisto(@RequestBody AddAcquistoCarteRequest request, @RequestHeader HttpHeaders header){
        return Mono.just(ResponseEntity.ok(acquistiWrapperService.addAcquisto(request,header)));
    }

    @PostMapping("getcarta")
    public Mono<ResponseEntity<GetAcquistiResponse>> getAcquistiResponse(@RequestBody GetAcquistiRequest request, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(acquistiWrapperService.getAcquisti(request,headers)));
    }

    @DeleteMapping("deletecarta/{codiceAcquisto}")
    public Mono<ResponseEntity<BaseResponse>> delteCartaAcquisto(@PathVariable ("codiceAcquisto") String codiceAcquisto, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(acquistiWrapperService.deleteAcquisto(codiceAcquisto,headers)));
    }

    @PatchMapping("updatecarta")
    public Mono<ResponseEntity<CarteAcquisto>> udpateCartaAcquisto(@RequestBody UpdateAcquistiCarteRequest request,@RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(acquistiWrapperService.updateAcquisto(request, headers)));
    }
}
