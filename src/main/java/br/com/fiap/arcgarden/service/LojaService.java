package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.model.Loja;
import br.com.fiap.arcgarden.repository.LojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService {

    private final LojaRepository repository;

    public LojaService(LojaRepository repository) {
        this.repository = repository;
    }

    public List<Loja> listarTodos() {
        return repository.readAll();
    }

    public Loja buscarPorId(int id) {
        Loja loja = repository.read(id);
        if (loja == null) {
            throw new RuntimeException("Loja não encontrada com ID: " + id);
        }
        return loja;
    }

    public Loja criar(Loja loja) {
        validar(loja);
        repository.create(loja);
        return loja;
    }

    public void atualizar(int id, Loja loja) {
        buscarPorId(id);
        validar(loja);
        loja.setId(id);
        repository.update(loja);
    }

    public void deletar(int id) {
        buscarPorId(id);
        repository.delete(id);
    }

    private void validar(Loja loja) {
        if (loja == null) {
            throw new IllegalArgumentException("Os dados da loja não podem ser nulos.");
        }
        if (loja.getAtualizacao() == null) {
            throw new IllegalArgumentException("A data de atualização é obrigatória.");
        }
    }
}
