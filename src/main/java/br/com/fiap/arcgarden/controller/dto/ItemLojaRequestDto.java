package br.com.fiap.arcgarden.controller.dto;

public record ItemLojaRequestDto(
        String nome,
        String tipo,
        double precoAgua,
        int lojaId
) {}
