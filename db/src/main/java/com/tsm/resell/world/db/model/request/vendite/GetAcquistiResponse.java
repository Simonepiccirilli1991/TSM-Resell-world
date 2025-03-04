package com.tsm.resell.world.db.model.request.vendite;

import com.tsm.resell.world.db.entity.CarteAcquisto;

import java.util.List;

public record GetAcquistiResponse(List<CarteAcquisto> listaAcquisti) {
}
