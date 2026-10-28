package br.com.fiap.arcgarden.controller.dto;

public record MissaoRequestDto(
        String nome,
        String descricao,
        String dificuldade,
        int vezes,
        int recompensaPontos
) {}