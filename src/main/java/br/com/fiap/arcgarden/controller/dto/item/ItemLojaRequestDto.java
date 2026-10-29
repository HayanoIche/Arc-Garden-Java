package br.com.fiap.arcgarden.controller.dto.item;

public record ItemLojaRequestDto(
        String nome,
        String tipo,
        double precoAgua,
        int lojaId
) {}
