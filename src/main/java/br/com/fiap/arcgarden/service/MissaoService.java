package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.repository.MissaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissaoService
{
    private final MissaoRepository repository;
    public MissaoService(MissaoRepository repository) { this.repository = repository; }

    public List<Missao> listarTodos() {
        return repository.readAll();
    }

    public Missao buscarPorId(int id) {
        Missao missao = repository.read(id);
        if (missao == null) {
            throw new RuntimeException("Missão não encontrada com ID: " + id);
        }
        return missao;
    }

    public Missao criar(Missao missao) {
        validar(missao);
        repository.create(missao);
        return missao;
    }

    public void atualizar(int id, Missao missao) {
        buscarPorId(id);
        validar(missao);
        missao.setId(id);
        repository.update(missao);
    }

    public void deletar(int id) {
        buscarPorId(id);
        repository.delete(id);
    }

    private void validar(Missao m) {
        if (m.getNome() == null || m.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da missão é obrigatório.");
        }
        if (m.getDificuldade() == null || (!m.getDificuldade().equals("F") && !m.getDificuldade().equals("M") && !m.getDificuldade().equals("D"))) {
            throw new IllegalArgumentException("A dificuldade deve ser 'F', 'M' ou 'D'.");
        }
        if (m.getVezes() <= 0) {
            throw new IllegalArgumentException("A quantidade de vezes deve ser maior que zero.");
        }
        if (m.getRecompensaPontos() < 0 || m.getRecompensaPontos() > 100) {
            throw new IllegalArgumentException("A recompensa em pontos deve estar entre 0 e 100.");
        }
    }
}