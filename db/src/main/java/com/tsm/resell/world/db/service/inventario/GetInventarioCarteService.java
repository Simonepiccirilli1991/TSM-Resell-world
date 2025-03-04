package com.tsm.resell.world.db.service.inventario;

import com.tsm.resell.world.db.entity.InventarioCarte;
import com.tsm.resell.world.db.model.request.inventario.GetInventarioRequest;
import com.tsm.resell.world.db.model.response.inventario.GetInventarioResponse;
import com.tsm.resell.world.db.repository.InventarioCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetInventarioCarteService {

    private final InventarioCarteRepo inventarioCarteRepo;

    public GetInventarioResponse getInventarioCarte(GetInventarioRequest request, HttpHeaders headers){

        var tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId")) ? headers.getFirst("tracingId") : UUID.randomUUID());

        log.info("GetInventarioCarteService started with raw request: {} , and tracingId: {}",request,tracingId);

        var resp = switch (request){

            case GetInventarioRequest i when !ObjectUtils.isEmpty(i.nomeAcquisto()) -> {
                log.info("GetInventarioCarteService filtering for nome acquisto");
                var entity = inventarioCarteRepo.findByNomeAcquisto(i.nomeAcquisto());

                if(entity.isPresent())
                    yield List.of(inventarioCarteRepo.findByNomeAcquisto(i.nomeAcquisto()).get());
                else
                    yield List.of();
            }

            case GetInventarioRequest i when !ObjectUtils.isEmpty(i.quantitaDisponibile()) -> {
                log.info("GetInventarioCarteService filtering for quantita disponibile");
                yield inventarioCarteRepo.filterQuantitaDisponibile(i.quantitaDisponibile());
            }

            case GetInventarioRequest i when !ObjectUtils.isEmpty(i.quantitaVenduta()) -> {
                log.info("GetInventarioCarteService filtering for quantita venduta");
                yield inventarioCarteRepo.filterQuantitaVenduta(i.quantitaVenduta());
            }

            default -> {
                log.error("GetInventarioCarteService not filter found, return all");
                yield inventarioCarteRepo.findAll();
            }
        };
        log.info("GetInventarioCarteService ended successfullY");
        return new GetInventarioResponse((List<InventarioCarte>) resp);
    }
}
