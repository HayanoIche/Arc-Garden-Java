package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class ItemLoja
{
    // Atributos
    private int id;
    private String nome;
    private String tipo;
    private double precoAgua;
    private int lojaId;

    // Construtores
    public ItemLoja() {}

    public ItemLoja(int id, String nome, String tipo, double precoAgua, int lojaId) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.precoAgua = precoAgua;
        this.lojaId = lojaId;
    }

    // Acessores
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public double getPrecoAgua() { return precoAgua; }
    public int getLojaId() { return lojaId; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPrecoAgua(double precoAgua) { this.precoAgua = precoAgua; }
    public void setLojaId(int lojaId) { this.lojaId = lojaId; }
}