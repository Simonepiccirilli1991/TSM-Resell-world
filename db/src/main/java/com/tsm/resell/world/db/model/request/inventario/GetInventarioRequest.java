package com.tsm.resell.world.db.model.request.inventario;

public record GetInventarioRequest(String nomeAcquisto, Integer quantitaDisponibile, Integer quantitaVenduta) {
}
