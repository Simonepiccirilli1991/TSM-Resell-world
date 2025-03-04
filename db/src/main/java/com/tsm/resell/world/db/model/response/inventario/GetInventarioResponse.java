package com.tsm.resell.world.db.model.response.inventario;

import com.tsm.resell.world.db.entity.InventarioCarte;

import java.util.List;

public record GetInventarioResponse(List<InventarioCarte> inventario) {
}
