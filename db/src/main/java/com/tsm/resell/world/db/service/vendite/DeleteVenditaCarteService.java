package com.tsm.resell.world.db.service.vendite;

import com.tsm.resell.world.db.exception.TsmDbException;
import com.tsm.resell.world.db.model.response.BaseResponse;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteVenditaCarteService {

    private final InventarioCarteRepo inventarioCarteRepo;
    private final VenditaCarteRepo venditaCarteRepo;

    @Transactional
    public BaseResponse deleteVendita(String chiaveVendita, HttpHeaders headers){

        var tracingId = (ObjectUtils.isEmpty(headers.getFirst("tracingId"))) ? UUID.randomUUID().toString() : headers.getFirst("tracingId");

        log.info("DeleteVenditaCarta service started for chiave: {} , and tracingId: {}",chiaveVendita,tracingId);

        var acquistoEnt = venditaCarteRepo.findByCodiceVendita(chiaveVendita)
                .orElseThrow(() -> {
                    log.error("Error on deleteVenditaCarta, missing vendita entity with this codiceVEndita: {}",chiaveVendita);
                    return new TsmDbException("Error on deleteVenditaCarta, chiave vendita non combacio","03");
                });

        var inventario = inventarioCarteRepo.findByNomeAcquisto(acquistoEnt.getNomeCarte())
                .orElseThrow(() -> {
                    log.error("Error on deleteVenditaCarta, can0t fined inventario with this name: {}",acquistoEnt.getNomeCarte());
                    return new TsmDbException("Error on deleteVenditaCarta, nomeCarta non combacio","04");
                });
        // calcolo storno da per aggiornare quantita vendita
        var quantitaStorno = acquistoEnt.getQuantitaVendita() + inventario.getQuantitaDisponibile();
        // calcolo update della quantia venduta
        var quantitaVenditaUpdate = inventario.getQuantitaVendute() - acquistoEnt.getQuantitaVendita();

        //update inventario
        inventario.setQuantitaDisponibile(quantitaStorno);
        inventario.setQuantitaVendute(quantitaVenditaUpdate);

        // salvo inventario e deleto entity
        inventarioCarteRepo.save(inventario);
        venditaCarteRepo.delete(acquistoEnt);

        log.info("DeleteVenditaCarta service ended successfully");
        return new BaseResponse("VEndita cancellata con successo","00");
    }
}
