package com.tsm.resell.world.db.model.response.acquisti;

import com.tsm.resell.world.db.entity.CarteAcquisto;

import java.util.List;

public record GetAcquistiResponse(List<CarteAcquisto> listaAcquisti) {
}
