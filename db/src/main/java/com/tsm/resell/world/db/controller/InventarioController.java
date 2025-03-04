package com.tsm.resell.world.db.controller;

import com.tsm.resell.world.db.model.request.inventario.GetInventarioRequest;
import com.tsm.resell.world.db.model.response.inventario.GetInventarioResponse;
import com.tsm.resell.world.db.service.inventario.GetInventarioCarteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final GetInventarioCarteService getInventarioCarteService;


    @PostMapping("getCarte")
    public Mono<ResponseEntity<GetInventarioResponse>> getInventarioCarte(@RequestBody GetInventarioRequest request, @RequestHeader HttpHeaders headers){
        return Mono.just(ResponseEntity.ok(getInventarioCarteService.getInventarioCarte(request,headers)));
    }
}
