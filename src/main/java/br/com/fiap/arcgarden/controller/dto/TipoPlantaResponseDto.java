package br.com.fiap.arcgarden.controller.dto;

public record TipoPlantaResponseDto(
        int id,
        String nome,
        String categoria,
        int xpMaximo,
        String descricao
) {}
