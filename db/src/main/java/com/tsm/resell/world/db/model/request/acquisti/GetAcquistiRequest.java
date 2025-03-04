package com.tsm.resell.world.db.model.request.acquisti;

public record GetAcquistiRequest(String nome, String codiceAcquisto, Double prezzoStart, Double prezzoEnd) {
}
