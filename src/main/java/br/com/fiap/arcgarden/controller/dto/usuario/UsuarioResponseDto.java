package br.com.fiap.arcgarden.controller.dto.usuario;

import br.com.fiap.arcgarden.controller.dto.item.ItemCompradoResponseDto;
import br.com.fiap.arcgarden.controller.dto.missao.MissaoConcluidaResponseDto;

import java.util.List;

public record UsuarioResponseDto(
        int id,
        String nome,
        String cpf,
        int arcScore,
        String status,
        String email,
        List<ItemCompradoResponseDto> itensComprados,
        List<MissaoConcluidaResponseDto> missoesConcluidas
) {}