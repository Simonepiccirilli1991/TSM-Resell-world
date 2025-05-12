package com.tsm.resell.world.db.service.vendite;

import com.tsm.resell.world.db.entity.CarteVendita;
import com.tsm.resell.world.db.model.request.vendite.GetVenditaCarteRequest;
import com.tsm.resell.world.db.repository.VenditaCarteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetVenditeCarteService {

    private final VenditaCarteRepo venditaCarteRepo;


    public List<CarteVendita> listaVendite(GetVenditaCarteRequest request, HttpHeaders headers){

        var tracingId = (!ObjectUtils.isEmpty(headers.getFirst("tracingId"))) ? headers.getFirst("tracingId") : UUID.randomUUID().toString();
        log.info("GetListaVenditeService started with raw request:{} , and tracingId: {}",request,tracingId);

        var resp = switch (request){
            // nome
            case GetVenditaCarteRequest i when !ObjectUtils.isEmpty(i.nome()) -> {
                log.info("Filtering for nome - tracingId: {}",tracingId);
                yield venditaCarteRepo.findByNomeCarte(i.nome());
            }
            // prezzo
            case GetVenditaCarteRequest i when !ObjectUtils.isEmpty(i.prezzoStart()) && !ObjectUtils.isEmpty(i.prezzoEnd()) -> {
                log.info("Filtering for fascia prezzo - tracingId: {}",tracingId);
                yield venditaCarteRepo.filterPerPrezzoVendita(i.prezzoStart(), i.prezzoEnd());
            }
            // data
            case GetVenditaCarteRequest i when !ObjectUtils.isEmpty(i.dataStart()) && !ObjectUtils.isEmpty(i.dataEnd()) -> {
                log.info("Filtering for fascia di date - tracingId: {}",tracingId);
                yield venditaCarteRepo.filterPerData(i.dataStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),i.dataEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            // quantita
            case GetVenditaCarteRequest i when !ObjectUtils.isEmpty(i.quantita()) -> {
                log.info("Filtering for quanita vendita maggiore di - tracingId: {}",tracingId);
                yield venditaCarteRepo.filtraPerQuantitaVendutaMaggioreDi(i.quantita());
            }
            default -> {
                log.info("Filtering returning all vendite -tracingId: {}",tracingId);
                yield venditaCarteRepo.findAll();
            }
        };

        log.info("GetListaVenditeService ended successfully - tracingId: {}",tracingId);
        return resp;
    }
}
