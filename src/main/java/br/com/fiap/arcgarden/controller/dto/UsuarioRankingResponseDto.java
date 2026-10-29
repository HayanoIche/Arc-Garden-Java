package br.com.fiap.arcgarden.controller.dto;

import java.util.List;

public record UsuarioRankingResponseDto(
        int id,
        int posicao,
        String nome,
        int arcScore,
        String email
) {}
