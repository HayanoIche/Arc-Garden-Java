package br.com.fiap.arcgarden.controller.dto.jardim;

import br.com.fiap.arcgarden.controller.dto.planta.PlantaResponseDto;

import java.util.List;

public record JardimResponseDto(
        int id,
        String nome,
        String tema,
        int usuarioId,
        List<PlantaResponseDto> plantas
) {}