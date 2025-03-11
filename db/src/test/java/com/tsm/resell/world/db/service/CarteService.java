package com.tsm.resell.world.db.service;


import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.acquisti.GetAcquistiRequest;
import com.tsm.resell.world.db.model.request.acquisti.UpdateAcquistiCarteRequest;
import com.tsm.resell.world.db.model.request.inventario.GetInventarioRequest;
import com.tsm.resell.world.db.repository.AcquistiCarteRepo;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.service.acquisti.AddAcquistiService;
import com.tsm.resell.world.db.service.acquisti.DeleteAcquistiService;
import com.tsm.resell.world.db.service.acquisti.GetAcquistiService;
import com.tsm.resell.world.db.service.acquisti.UpdateAcquistiService;
import com.tsm.resell.world.db.service.inventario.GetInventarioCarteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

@SpringBootTest
public class CarteService {

    // service
    @Autowired
    AddAcquistiService addAcquistiService;
    @Autowired
    GetAcquistiService getAcquistiService;
    @Autowired
    GetInventarioCarteService getInventarioCarteService;
    @Autowired
    DeleteAcquistiService deleteAcquistiService;
    @Autowired
    UpdateAcquistiService updateAcquistiService;

    // repo
    @Autowired
    AcquistiCarteRepo acquistiCarteRepo;
    @Autowired
    InventarioCarteRepo inventarioCarteRepo;

    // mi ripulisce il db dopo ogni singolo test
    @AfterEach
    void tearDown() {
        acquistiCarteRepo.deleteAll();
        inventarioCarteRepo.deleteAll();
    }

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

    @Test
    void getInventarioMultiploTestOK(){

        var request = new AddAcquistoCarteRequest("ETB destino sfuggente", LocalDateTime.now().minusHours(1),71.00,3,"Carrefout",
                "destino sfuggente","pokemon","sealed",false,"evlprsm123");

        addAcquistiService.addAcquistoCarte(request,new HttpHeaders());

        var iRequest = new AddAcquistoCarteRequest("ETB destino sfuggente", LocalDateTime.now(),54.00,2,"Carrefout",
                "destino sfuggente","pokemon","sealed",false,"evlprsm123");

        var resp = addAcquistiService.addAcquistoCarte(iRequest,new HttpHeaders());

        var iRequestF = new GetInventarioRequest(iRequest.nomeAcquisto(),null,null);
        // ttesto calcolo corretto
        var iResp = getInventarioCarteService.getInventarioCarte(iRequestF,new HttpHeaders());

        Assertions.assertEquals(5,iResp.inventario().getFirst().getQuantitaDisponibile());

        // test Delete acquisto
        deleteAcquistiService.deleteAcquistoCarte(resp.getCodiceAcquisto(),new HttpHeaders());

        var iResp2 = getInventarioCarteService.getInventarioCarte(iRequestF,new HttpHeaders());

        Assertions.assertEquals(3,iResp2.inventario().getFirst().getQuantitaDisponibile());
        Assertions.assertEquals(1,iResp2.inventario().getFirst().getCodiciAcquisti().size());
    }

    @Test
    void updateCartaAcqusitoTestOK(){

        var request = new AddAcquistoCarteRequest("ETB evoluzioni a paldea", LocalDateTime.now().minusHours(1),71.00,3,"Carrefout",
                "destino sfuggente","pokemon","sealed",false,"evlprsm123");

        var resp = addAcquistiService.addAcquistoCarte(request,new HttpHeaders());

        Assertions.assertEquals("ETB evoluzioni a paldea",resp.getNomeAcquisto());

        var iRequest = new GetAcquistiRequest(null, resp.getCodiceAcquisto(), null,null);
        var iResp = getAcquistiService.getCarteAcquisto(iRequest,new HttpHeaders());

        Assertions.assertEquals(3,iResp.listaAcquisti().getFirst().getQuantitaAcquistata());

        var iRequestF = new GetInventarioRequest(request.nomeAcquisto(),null,null);
        // ttesto calcolo corretto
        var iRespF = getInventarioCarteService.getInventarioCarte(iRequestF,new HttpHeaders());
        Assertions.assertEquals(3,iRespF.inventario().getFirst().getQuantitaDisponibile());

        var updateRequest = new AddAcquistoCarteRequest("ETB evoluzioni a paldea", null,50.00,5,"Carrefour",
                "evoluzioni a paldea",null,null,null,null);

        // vado di udpate e richecko dopo
        var iUpdateRequest = new UpdateAcquistiCarteRequest(resp.getCodiceAcquisto(), updateRequest);

        var finalResp = updateAcquistiService.updateCarteAcquisto(iUpdateRequest,new HttpHeaders());

        Assertions.assertEquals(5,finalResp.getQuantitaAcquistata());
        Assertions.assertEquals(250.00,finalResp.getCostoTotaleAcquisto());

        var finalInventario = getInventarioCarteService.getInventarioCarte(iRequestF,new HttpHeaders());

        Assertions.assertEquals(5,finalInventario.inventario().getFirst().getQuantitaDisponibile());
    }
}
