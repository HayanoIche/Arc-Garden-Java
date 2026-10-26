package br.com.fiap.arcgarden.service;

import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.repository.ItemLojaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemLojaService
{
    private final ItemLojaRepository repository;
    public ItemLojaService(ItemLojaRepository repository) { this.repository = repository; }

    public List<ItemLoja> listarTodos() {
        return repository.readAll();
    }

    public ItemLoja buscarPorId(int id) {
        ItemLoja item = repository.read(id);
        if (item == null) {
            throw new RuntimeException("Item da loja não encontrado com o ID: " + id);
        }
        return item;
    }

    public List<ItemLoja> buscarPorTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo para busca não pode ser vazio.");
        }
        return repository.readByTipo(tipo);
    }

    public ItemLoja criar(ItemLoja item) {
        validarItem(item);
        repository.create(item);
        return item;
    }

    public void atualizar(int id, ItemLoja item) {
        buscarPorId(id);
        validarItem(item);
        item.setId(id);
        repository.update(item);
    }

    public void deletar(int id) {
        buscarPorId(id);
        repository.delete(id);
    }

    private void validarItem(ItemLoja item) {
        if (item == null) {
            throw new IllegalArgumentException("Os dados do item não podem ser nulos.");
        }
        if (item.getNome() == null || item.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do item da loja é obrigatório.");
        }
        if (item.getTipo() == null || item.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do item da loja é obrigatório.");
        }
        if (item.getPrecoAgua() < 0) {
            throw new IllegalArgumentException("O preço em água não pode ser negativo.");
        }
        if (item.getLojaId() <= 0) {
            throw new IllegalArgumentException("O ID da loja associada é inválido.");
        }
    }
}