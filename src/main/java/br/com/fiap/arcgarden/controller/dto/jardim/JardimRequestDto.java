package br.com.fiap.arcgarden.controller.dto.jardim;

public record JardimRequestDto(
        String nome,
        String tema,
        int usuarioId
) {}
