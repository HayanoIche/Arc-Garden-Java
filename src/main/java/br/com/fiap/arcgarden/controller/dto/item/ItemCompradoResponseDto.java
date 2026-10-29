package br.com.fiap.arcgarden.controller.dto.item;

import java.time.LocalDate;

public record ItemCompradoResponseDto(
        int id,
        LocalDate dataCompra,
        int quantidade,
        String nomeItem,
        double precoAgua
) {}
