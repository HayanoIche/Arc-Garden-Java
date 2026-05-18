package br.com.tdspk.arcgarden.model;

public class Planta
{
    // Atributos
    private String nome;
    private String raridade;
    private int level;
    private boolean premiada;
    private String tipo;

    // To String
    @Override
    public String toString()
    {
        return nome +
               "\n  Raridade: " + raridade +
               "\n  Level: " + level +
               "\n  Brilhante: " + premiada +
               "\n  Tipo: " + tipo + "\n";
    }

    // Construtores
    // Construtor Vazio
    public Planta() {}

    // Construtor Cheio
    public Planta(String nome, String raridade, int level, boolean premiada, String tipo)
    {
        this.nome = nome;
        this.raridade = raridade;
        this.level = level;
        this.premiada = premiada;
        this.tipo = tipo;
    }

    // Metodos acessores
    public String getNome() { return nome; }
    public String getRaridade() { return raridade; }
    public int getLevel() { return level; }
    public boolean isPremiada() { return premiada; }
    public String getTipo() { return tipo; }

    public void setNome(String nome) { this.nome = nome; }
    public void setRaridade(String raridade) { this.raridade = raridade; }
    public void setLevel(int level) { this.level = level; }
    public void setPremiada(boolean premiada) { this.premiada = premiada; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
