package br.com.fiap.arcgarden.controller.dto.planta;

public record TipoPlantaRequestDto(
        String nome,
        String categoria,
        int xpMaximo,
        String descricao
) {}
