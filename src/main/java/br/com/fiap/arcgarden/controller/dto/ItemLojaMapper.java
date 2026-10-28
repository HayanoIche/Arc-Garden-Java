package br.com.fiap.arcgarden.controller.dto;

import br.com.fiap.arcgarden.model.ItemLoja;

public class ItemLojaMapper {

    public static ItemLoja toModel(ItemLojaRequestDto dto) {
        return new ItemLoja(
                0,
                dto.nome(),
                dto.tipo(),
                dto.precoAgua(),
                dto.lojaId()
        );
    }

    public static ItemLoja toModel(int id, ItemLojaRequestDto dto) {
        return new ItemLoja(
                id,
                dto.nome(),
                dto.tipo(),
                dto.precoAgua(),
                dto.lojaId()
        );
    }

    public static ItemLojaResponseDto toResponseDto(ItemLoja item) {
        return new ItemLojaResponseDto(
                (int) item.getId(),
                item.getNome(),
                item.getTipo(),
                item.getPrecoAgua(),
                item.getLojaId()
        );
    }
}