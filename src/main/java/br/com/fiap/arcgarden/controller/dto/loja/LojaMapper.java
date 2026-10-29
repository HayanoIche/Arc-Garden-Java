package br.com.fiap.arcgarden.controller.dto.loja;

import br.com.fiap.arcgarden.model.Loja;

public class LojaMapper
{
    public static Loja toModel(LojaRequestDto dto) {
        if (dto == null) return null;
        Loja loja = new Loja();
        loja.setAtualizacao(dto.atualizacao());
        return loja;
    }

    public static Loja toModel(int id, LojaRequestDto dto) {
        Loja loja = toModel(dto);
        if (loja != null) loja.setId(id);
        return loja;
    }

    public static LojaResponseDto toResponseDto(Loja loja) {
        if (loja == null) return null;
        return new LojaResponseDto(
                loja.getId(),
                loja.getAtualizacao()
        );
    }
}