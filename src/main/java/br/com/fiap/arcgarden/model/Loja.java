package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class Loja
{
    // Atributos
    private int id;
    private LocalDate atualizacao;

    // Construtores
    public Loja() {}

    public Loja(int id, LocalDate atualizacao) {
        this.id = id;
        this.atualizacao = atualizacao;
    }

    // Acessores
    public int getId() { return id; }
    public LocalDate getAtualizacao() { return atualizacao; }

    public void setId(int id) { this.id = id; }
    public void setAtualizacao(LocalDate atualizacao) { this.atualizacao = atualizacao; }
}
