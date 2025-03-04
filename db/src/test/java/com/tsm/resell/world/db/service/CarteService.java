package com.tsm.resell.world.db.service;


import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.service.acquisti.AddAcquistiService;
import com.tsm.resell.world.db.service.acquisti.GetAcquistiService;
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
}
