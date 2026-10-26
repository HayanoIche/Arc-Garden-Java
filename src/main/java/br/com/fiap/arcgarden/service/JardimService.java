package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.model.Jardim;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.repository.JardimRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JardimService
{
    private final JardimRepository repository;
    public JardimService(JardimRepository repository) { this.repository = repository; }

    public List<Jardim> listarTodos() {
        return repository.readAll();
    }

    public Jardim buscarPorId(int id) {
        Jardim jardim = repository.read(id);
        if (jardim == null) {
            throw new RuntimeException("Jardim não encontrado com o ID: " + id);
        }
        return jardim;
    }

    public Jardim buscarPorUsuarioId(int usuarioId) {
        Jardim jardim = repository.readByUsuarioId(usuarioId);
        if (jardim == null) {
            throw new RuntimeException("Jardim não encontrado para o usuário com ID: " + usuarioId);
        }
        return jardim;
    }

    public List<Jardim> buscarPorTema(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new IllegalArgumentException("O tema para busca não pode ser vazio.");
        }
        return repository.readByTema(tema);

    }

    public void adicionarPlanta(int jardimId, Planta planta) {
        buscarPorId(jardimId); // Garante que o jardim existe
        if (planta.getTipoPlanta() == null || planta.getTipoPlanta().getId() <= 0) {
            throw new IllegalArgumentException("O ID do tipo de planta é obrigatório.");
        }
        repository.inserirPlanta(jardimId, planta);
    }

    public Jardim criar(Jardim jardim) {
        validarJardim(jardim);
        repository.create(jardim);
        return jardim;
    }

    public void atualizar(int id, Jardim jardim) {
        buscarPorId(id);
        validarJardim(jardim);
        jardim.setId(id);
        repository.update(jardim);
    }

    public void deletar(int id) {
        buscarPorId(id);
        repository.delete(id);
    }

    private void validarJardim(Jardim jardim) {
        if (jardim == null) {
            throw new IllegalArgumentException("Os dados do jardim não podem ser nulos.");
        }
        if (jardim.getNome() == null || jardim.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jardim é obrigatório.");
        }
        if (jardim.getTema() == null || jardim.getTema().trim().isEmpty()) {
            throw new IllegalArgumentException("O tema do jardim é obrigatório.");
        }
        if (jardim.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("O ID do usuário associado ao jardim é inválido.");
        }
    }
}