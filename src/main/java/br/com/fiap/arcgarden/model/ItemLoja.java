package br.com.fiap.arcgarden.model;

import java.math.BigDecimal;

public class ItemLoja {

    private int id;
    private String nome;
    private String tipo; // COSMETICO, MELHORIA, ROLETA, SEMENTE
    private BigDecimal precoAgua;

    public ItemLoja() {
    }

    public ItemLoja(int id, String nome, String tipo, BigDecimal precoAgua) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.precoAgua = precoAgua;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public BigDecimal getPrecoAgua() { return precoAgua; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPrecoAgua(BigDecimal precoAgua) { this.precoAgua = precoAgua; }
}