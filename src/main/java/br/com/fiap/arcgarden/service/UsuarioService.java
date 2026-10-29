package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.controller.dto.UsuarioResponseDto;
import br.com.fiap.arcgarden.model.Usuario;
import br.com.fiap.arcgarden.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService
{
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository)  { this.repository = repository; }

    public Usuario criar(Usuario usuario)
    {
        repository.create(usuario);
        return usuario;
    }

    public List<Usuario> listar()
    {
        return repository.readAll();
    }

    public List<Usuario> listarRanking()
    {
        return repository.readRanking();
    }

    public Usuario buscarPorPosicaoRanking(int posicao)
    {
        List<Usuario> users = listarRanking();
        return users.get(posicao-1);
    }


    public Usuario buscarPorId(int id)
    {
        return repository.read(id);
    }

    public void atualizar(int id, Usuario usuario)
    {
        usuario.setId(id);
        repository.update(usuario);
    }

    public void concluirMissao(int usuarioId, int missaoId)
    {
        repository.inserirMissaoConcluida(usuarioId, missaoId);
    }

    public void comprarItem(int usuarioId, int itemId, int quantidade)
    {
        if (quantidade <= 0)
        {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        repository.inserirItemComprado(usuarioId, itemId, quantidade);
    }
}