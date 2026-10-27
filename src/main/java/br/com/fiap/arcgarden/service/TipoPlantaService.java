package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.model.TipoPlanta;
import br.com.fiap.arcgarden.repository.TipoPlantaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPlantaService
{
    private final TipoPlantaRepository repository;

    public TipoPlantaService(TipoPlantaRepository repository) {
        this.repository = repository;
    }

    public List<TipoPlanta> listarTodos() {
        return repository.readAll();
    }

    public TipoPlanta buscarPorId(int id) {
        TipoPlanta tipo = repository.read(id);
        if (tipo == null) {
            throw new RuntimeException("Tipo de planta não encontrado com ID: " + id);
        }
        return tipo;
    }

    public List<TipoPlanta> buscarPorCategoria(String categoria) {
        return repository.readByCategoria(categoria);
    }

    public TipoPlanta criar(TipoPlanta tipo) {
        validar(tipo);
        repository.create(tipo);
        return tipo;
    }

    public void atualizar(int id, TipoPlanta tipo) {
        buscarPorId(id);
        validar(tipo);
        tipo.setId(id);
        repository.update(tipo);
    }

    public void deletar(int id) {
        buscarPorId(id);
        repository.delete(id);
    }

    private void validar(TipoPlanta t) {
        if (t.getNome() == null || t.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do tipo de planta é obrigatório.");
        }
        if (t.getCategoria() == null || t.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        if (t.getXpMaximo() <= 0) {
            throw new IllegalArgumentException("O XP máximo deve ser maior que zero.");
        }
    }
}
