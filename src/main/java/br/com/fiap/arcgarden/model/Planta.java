package br.com.fiap.arcgarden.model;

public class Planta
{
    // Atributos
    private int id;
    private String nome;
    private String categoria;
    private int xpMaximo;
    private String descricao;

    // Construtores
    public Planta(String nome, String categoria, int xpMaximo, String descricao)
    {
        this.nome = nome;
        this.categoria = categoria;
        this.xpMaximo = xpMaximo;
        this.descricao = descricao;
    }

    public Planta() {}

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCategoria() { return categoria; }
    public int getXpMaximo() { return xpMaximo; }
    public String getDescricao() { return descricao; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setXpMaximo(int xp_maximo) { this.xpMaximo = xp_maximo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
