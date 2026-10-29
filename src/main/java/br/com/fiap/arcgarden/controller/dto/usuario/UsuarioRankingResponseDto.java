package br.com.fiap.arcgarden.controller.dto.usuario;

public record UsuarioRankingResponseDto(
        int id,
        int posicao,
        String nome,
        int arcScore,
        String email
) {}
