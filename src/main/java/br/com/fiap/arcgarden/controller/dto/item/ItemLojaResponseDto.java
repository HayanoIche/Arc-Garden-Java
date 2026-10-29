package br.com.fiap.arcgarden.controller.dto.item;

public record ItemLojaResponseDto(
        int id,
        String nome,
        String tipo,
        double precoAgua,
        int lojaId
) {}
