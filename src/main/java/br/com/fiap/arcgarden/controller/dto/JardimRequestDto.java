package br.com.fiap.arcgarden.controller.dto;

public record JardimRequestDto(
        String nome,
        String tema,
        int usuarioId
) {}
