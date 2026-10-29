package br.com.fiap.arcgarden.controller.dto.loja;

import java.time.LocalDate;

public record LojaRequestDto(
        LocalDate atualizacao
) {}
