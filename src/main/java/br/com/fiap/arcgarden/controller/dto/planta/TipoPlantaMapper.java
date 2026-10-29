package br.com.fiap.arcgarden.controller.dto.planta;

import br.com.fiap.arcgarden.model.TipoPlanta;

public class TipoPlantaMapper
{
    public static TipoPlanta toModel(TipoPlantaRequestDto dto)
    {
        if (dto == null) return null;
        TipoPlanta t = new TipoPlanta();
        t.setNome(dto.nome());
        t.setCategoria(dto.categoria());
        t.setXpMaximo(dto.xpMaximo());
        t.setDescricao(dto.descricao());
        return t;
    }

    public static TipoPlanta toModel(int id, TipoPlantaRequestDto dto)
    {
        TipoPlanta t = toModel(dto);
        if (t != null) t.setId(id);
        return t;
    }

    public static TipoPlantaResponseDto toResponseDto(TipoPlanta t)
    {
        if (t == null) return null;
        return new TipoPlantaResponseDto(
                t.getId(),
                t.getNome(),
                t.getCategoria(),
                t.getXpMaximo(),
                t.getDescricao()
        );
    }
}