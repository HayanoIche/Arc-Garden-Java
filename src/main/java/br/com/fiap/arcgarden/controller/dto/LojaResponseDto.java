package br.com.fiap.arcgarden.controller.dto;

import java.time.LocalDate;

public record LojaResponseDto(
        int id,
        LocalDate atualizacao
) {}
