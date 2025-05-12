package com.tsm.resell.world.db.service;

import com.tsm.resell.world.db.model.request.acquisti.AddAcquistoCarteRequest;
import com.tsm.resell.world.db.model.request.inventario.GetInventarioRequest;
import com.tsm.resell.world.db.model.request.vendite.AddVenditeCarteRequest;
import com.tsm.resell.world.db.model.request.vendite.UpdateVenditaCarteRequest;
import com.tsm.resell.world.db.service.acquisti.AcquistiWrapperService;
import com.tsm.resell.world.db.service.inventario.GetInventarioCarteService;
import com.tsm.resell.world.db.service.vendite.VenditaWrapperService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

@SpringBootTest
public class CarteVenditaServiceTest {

    @Autowired
    AcquistiWrapperService acquistiWrapperService;
    @Autowired
    GetInventarioCarteService getInventarioCarteService;
    @Autowired
    VenditaWrapperService venditaWrapperService;

    @Test
    void addAcquistoEGetInventarioTestAndVEnditaOK(){

        var request = new AddAcquistoCarteRequest("ETB corona astrale", LocalDateTime.now(),61.00,3,"Carrefout",
                "destino di paldea","pokemon","sealed",false,"evlprsm123");

        var resp = acquistiWrapperService.addAcquisto(request,new HttpHeaders());

        Assertions.assertEquals("ETB corona astrale",resp.getNomeAcquisto());

        var iRequest = new GetInventarioRequest(null,3,null);

        var iResp = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals("ETB corona astrale",iResp.inventario().getFirst().getNomeAcquisto());

        var venditaRequest = new AddVenditeCarteRequest("ETB corona astrale","Pokemon","Sealed","Corona Astrale",LocalDateTime.now(),65.00,2,
                "Vinted","00100",5.12,resp.getCodiceAcquisto(),false);

        var headers = new HttpHeaders();
        headers.add("requestId","asd1234");

        var venditaResp = venditaWrapperService.addVenditaCarte(venditaRequest,headers);

        Assertions.assertEquals("ETB corona astrale",venditaResp.getNomeCarte());
    }

    @Test
    void addAcquistoEGetInventarioTestAndVEnditaEDeleteOK(){

        var request = new AddAcquistoCarteRequest("ETB astri lucenti", LocalDateTime.now(),61.00,3,"Carrefout",
                "destino di paldea","pokemon","sealed",false,"evlprsm123");
        // salvo acquisto
        var resp = acquistiWrapperService.addAcquisto(request,new HttpHeaders());

        Assertions.assertEquals("ETB astri lucenti",resp.getNomeAcquisto());

        var iRequest = new GetInventarioRequest("ETB astri lucenti",null,null);
        // checko inventario
        var iResp = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals("ETB astri lucenti",iResp.inventario().getFirst().getNomeAcquisto());

        var venditaRequest = new AddVenditeCarteRequest("ETB astri lucenti","Pokemon","Sealed","Corona Astrale",LocalDateTime.now(),65.00,2,
                "Vinted","00102",5.12,resp.getCodiceAcquisto(),false);

        var headers = new HttpHeaders();
        headers.add("requestId","asd1234");
        // agiungo vendita
        var venditaResp = venditaWrapperService.addVenditaCarte(venditaRequest,headers);

        Assertions.assertEquals("ETB astri lucenti",venditaResp.getNomeCarte());
        // controllo se scala correttamente
        var iResp2 = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals(1,iResp2.inventario().getFirst().getQuantitaDisponibile());

        // cancello vendita
        venditaWrapperService.deleteCarteVendita(venditaResp.getCodiceVendita(),headers);

        // checko inventario rollback
        var iResp3 = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals(3,iResp3.inventario().getFirst().getQuantitaDisponibile());
    }

    @Test
    void addAcquistoEGetInventarioTestAndVEnditaEUpdateOK(){

        var request = new AddAcquistoCarteRequest("ETB origine perduta", LocalDateTime.now(),61.00,3,"Carrefout",
                "destino di paldea","pokemon","sealed",false,"evlprsm123");
        // salvo acquisto
        var resp = acquistiWrapperService.addAcquisto(request,new HttpHeaders());

        Assertions.assertEquals("ETB origine perduta",resp.getNomeAcquisto());

        var iRequest = new GetInventarioRequest("ETB origine perduta",null,null);
        // checko inventario
        var iResp = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals("ETB origine perduta",iResp.inventario().getFirst().getNomeAcquisto());

        var venditaRequest = new AddVenditeCarteRequest("ETB origine perduta","Pokemon","Sealed","Corona Astrale",LocalDateTime.now(),65.00,2,
                "Vinted","00102",5.12,resp.getCodiceAcquisto(),false);

        var headers = new HttpHeaders();
        headers.add("requestId","asd1234");
        // agiungo vendita
        var venditaResp = venditaWrapperService.addVenditaCarte(venditaRequest,headers);

        Assertions.assertEquals("ETB origine perduta",venditaResp.getNomeCarte());
        // controllo se scala correttamente
        var iResp2 = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals(1,iResp2.inventario().getFirst().getQuantitaDisponibile());

        var updateReq = new AddVenditeCarteRequest(null,null,"Usata bene",null,null,72.00,3,null,null,7.12,null,null);
        var updateRequest = new UpdateVenditaCarteRequest(venditaResp.getCodiceVendita(),updateReq);
        // update
        var resp3 = venditaWrapperService.updateCarteVendita(updateRequest,headers);

        Assertions.assertEquals("Usata bene",resp3.getCondizioniCarta());
        Assertions.assertNotEquals(venditaResp.getPrezzoTotaleVendita(),resp3.getPrezzoTotaleVendita());
        // checko inventario rollback
        var iResp3 = getInventarioCarteService.getInventarioCarte(iRequest,new HttpHeaders());

        Assertions.assertEquals(0,iResp3.inventario().getFirst().getQuantitaDisponibile());
    }
}
