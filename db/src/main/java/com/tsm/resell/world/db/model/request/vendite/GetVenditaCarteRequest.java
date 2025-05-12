package com.tsm.resell.world.db.model.request.vendite;

import java.time.LocalDateTime;

public record GetVenditaCarteRequest(String nome, Double prezzoStart, Double prezzoEnd, LocalDateTime dataStart,LocalDateTime dataEnd, Integer quantita) {
}
