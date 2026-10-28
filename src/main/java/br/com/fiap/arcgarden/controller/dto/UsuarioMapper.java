package br.com.fiap.arcgarden.controller.dto;

import br.com.fiap.arcgarden.model.Usuario;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper
{
    public static Usuario toModel(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setArcScore(dto.arcScore() > 0 ? dto.arcScore() : 0);
        usuario.setStatus(dto.status());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    public static UsuarioResponseDto toResponseDto(Usuario usuario) {
        if (usuario == null) return null;

        List<ItemCompradoResponseDto> itensDto = usuario.getItensComprados() != null ?
                usuario.getItensComprados().stream().map(item -> new ItemCompradoResponseDto(
                        item.getId(),
                        item.getDataCompra(),
                        item.getQuantidade(),
                        item.getItemLoja() != null ? item.getItemLoja().getNome() : null,
                        item.getItemLoja() != null ? item.getItemLoja().getPrecoAgua() : 0.0
                )).collect(Collectors.toList()) : Collections.emptyList();

        List<MissaoConcluidaResponseDto> missoesDto = usuario.getMissoesConcluidas() != null ?
                usuario.getMissoesConcluidas().stream().map(mc -> new MissaoConcluidaResponseDto(
                        mc.getId(),
                        mc.getDataConclusao(),
                        mc.getPontosGanhos(),
                        mc.getMissao() != null ? mc.getMissao().getNome() : null,
                        mc.getMissao() != null ? mc.getMissao().getDescricao() : null,
                        mc.getMissao() != null ? mc.getMissao().getDificuldade() : null
                )).collect(Collectors.toList()) : Collections.emptyList();

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getArcScore(),
                usuario.getStatus(),
                usuario.getEmail(),
                itensDto,
                missoesDto
        );
    }
}