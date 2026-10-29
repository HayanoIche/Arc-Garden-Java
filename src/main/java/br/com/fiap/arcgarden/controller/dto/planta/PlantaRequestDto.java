package br.com.fiap.arcgarden.controller.dto.planta;

public record PlantaRequestDto(
        int tipoId,
        String raridade,
        String nivelPlanta,
        String premiada,
        int xpAtual
) {}
