package br.com.fiap.arcgarden.controller.dto;

public record PlantaResponseDto(
        int id,
        String raridade,
        String nivelPlanta,
        String premiada,
        int xpAtual,
        TipoPlantaResponseDto tipoPlanta
) {}
