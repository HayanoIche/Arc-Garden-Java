package br.com.fiap.arcgarden.controller.dto.planta;

public record PlantaResponseDto(
        int id,
        String raridade,
        String nivelPlanta,
        String premiada,
        int xpAtual,
        TipoPlantaResponseDto tipoPlanta
) {}
