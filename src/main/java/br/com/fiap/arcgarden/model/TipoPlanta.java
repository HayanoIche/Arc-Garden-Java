package br.com.fiap.arcgarden.model;

public class TipoPlanta
{
    // Atributos
    private int id;
    private String nome;
    private String categoria;
    private int xpMaximo;
    private String descricao;

    // Construtores
    public TipoPlanta() {}

    public TipoPlanta(int id, String nome, String categoria, int xpMaximo, String descricao) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.xpMaximo = xpMaximo;
        this.descricao = descricao;
    }

    // Acessores
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public int getXpMaximo() { return xpMaximo; }
    public String getDescricao() { return descricao; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setXpMaximo(int xpMaximo) { this.xpMaximo = xpMaximo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}