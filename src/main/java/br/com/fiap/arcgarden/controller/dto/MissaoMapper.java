package br.com.fiap.arcgarden.controller.dto;

import br.com.fiap.arcgarden.model.Missao;

public class MissaoMapper
{
    public static Missao toModel(MissaoRequestDto dto)
    {
        if (dto == null) return null;
        Missao m = new Missao();
        m.setNome(dto.nome());
        m.setDescricao(dto.descricao());
        m.setDificuldade(dto.dificuldade());
        m.setVezes(dto.vezes());
        m.setRecompensaPontos(dto.recompensaPontos());
        return m;
    }

    public static Missao toModel(int id, MissaoRequestDto dto)
    {
        Missao m = toModel(dto);
        if (m != null) m.setId(id);
        return m;
    }

    public static MissaoResponseDto toResponseDto(Missao m)
    {
        if (m == null) return null;
        return new MissaoResponseDto(
                m.getId(),
                m.getNome(),
                m.getDescricao(),
                m.getDificuldade(),
                m.getVezes(),
                m.getRecompensaPontos()
        );
    }
}