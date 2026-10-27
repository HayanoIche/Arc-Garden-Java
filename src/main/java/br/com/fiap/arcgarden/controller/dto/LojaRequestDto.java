package br.com.fiap.arcgarden.controller.dto;

import java.time.LocalDate;

public record LojaRequestDto(
        LocalDate atualizacao
) {}
