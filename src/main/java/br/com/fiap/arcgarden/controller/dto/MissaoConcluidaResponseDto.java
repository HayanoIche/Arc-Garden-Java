package br.com.fiap.arcgarden.controller.dto;

import java.time.LocalDate;

public record MissaoConcluidaResponseDto(
        int id,
        LocalDate dataConclusao,
        int pontosGanhos,
        String nomeMissao,
        String descricaoMissao,
        String dificuldade
) {}