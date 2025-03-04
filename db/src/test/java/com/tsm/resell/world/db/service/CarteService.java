package com.tsm.resell.world.db.service;


import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.inventario.GetInventarioRequest;
import com.tsm.resell.world.db.service.acquisti.AddAcquistiService;
import com.tsm.resell.world.db.service.acquisti.GetAcquistiService;
import com.tsm.resell.world.db.service.inventario.GetInventarioCarteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

@SpringBootTest
public class CarteService {

    @Autowired
    AddAcquistiService addAcquistiService;
    @Autowired
    GetAcquistiService getAcquistiService;
    @Autowired
    GetInventarioCarteService getInventarioCarteService;



    @Test
    void addAcquistiTestOK(){

        var request = new AddAcquistoCarteRequest("ETB scintille folgoranti", LocalDateTime.now(),61.00,2,"Carrefout",
                "scintille folgoranti","pokemon","sealed",false,"hgkd123");

        var headers = new HttpHeaders();
        headers.add("requestId","asd1234");
        var resp = addAcquistiService.addAcquistoCarte(request,headers);

        Assertions.assertEquals("ETB scintille folgoranti",resp.getNomeAcquisto());
        Assertions.assertEquals(122.00,resp.getCostoTotaleAcquisto());
    }

    @Test
    void addAcquistiAndGetTestOK(){

        var request = new AddAcquistoCarteRequest("ETB evoluzioni prismatiche", LocalDateTime.now(),61.00,2,"Carrefout",
                "evoluzioni prismatiche","pokemon","sealed",false,"evlprsm123");

        var resp = addAcquistiService.addAcquistoCarte(request,new HttpHeaders());

        Assertions.assertEquals("ETB evoluzioni prismatiche",resp.getNomeAcquisto());

        var iRequest = new GetAcquistiRequest("ETB evoluzioni prismatiche",null,null,null);
        var iResp = getAcquistiService.getCarteAcquisto(iRequest,new HttpHeaders());

        System.out.print(iResp);
        Assertions.assertEquals(122.00,iResp.listaAcquisti().getFirst().getCostoTotaleAcquisto());
    }

    @Test
    void addAcquistoEGetInventarioTestOK(){

        var request = new AddAcquistoCarteRequest("ETB destino di paldea", LocalDateTime.now(),61.00,1,"Carrefout",
                "destino di paldea","pokemon","sealed",false,"evlprsm123");

        var resp = addAcquistiService.addAcquistoCarte(request,new HttpHeaders());

        Assertions.assertEquals("ETB destino di paldea",resp.getNomeAcquisto());

        var iRequest = new GetInventarioRequest(null,1,null);

        var iResp = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals("ETB destino di paldea",iResp.inventario().getFirst().getNomeAcquisto());

    }
}
