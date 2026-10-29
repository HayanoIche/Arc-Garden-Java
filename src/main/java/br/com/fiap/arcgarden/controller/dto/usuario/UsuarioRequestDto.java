package br.com.fiap.arcgarden.controller.dto.usuario;

public record UsuarioRequestDto(
        String nome,
        String cpf,
        int arcScore,
        String status,
        String email,
        String senha
) {}
