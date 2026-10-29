package br.com.fiap.arcgarden.controller.dto.missao;

public record MissaoResponseDto(
        int id,
        String nome,
        String descricao,
        String dificuldade,
        int vezes,
        int recompensaPontos
) {}