package br.com.fiap.arcgarden.controller.dto.planta;

public record TipoPlantaResponseDto(
        int id,
        String nome,
        String categoria,
        int xpMaximo,
        String descricao
) {}
