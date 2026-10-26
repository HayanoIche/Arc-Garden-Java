package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class ItemComprado
{
    // Atributos
    private int id;
    private LocalDate dataCompra;
    private int quantidade;
    private int usuarioId;
    private ItemLoja itemLoja;

    // Construtores
    public ItemComprado() {}

    public ItemComprado(int id, LocalDate dataCompra, int quantidade, int usuarioId, ItemLoja itemLoja) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.quantidade = quantidade;
        this.usuarioId = usuarioId;
        this.itemLoja = itemLoja;
    }

    // Acessores
    public int getId() { return id; }
    public LocalDate getDataCompra() { return dataCompra; }
    public int getQuantidade() { return quantidade; }
    public int getUsuarioId() { return usuarioId; }
    public ItemLoja getItemLoja() { return itemLoja; }

    public void setId(int id) { this.id = id; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setItemLoja(ItemLoja itemLoja) { this.itemLoja = itemLoja; }
}