package br.com.fiap.arcgarden.model;

public class Planta
{
    // Atributos
    private int id;
    private String raridade;
    private int level;
    private boolean premiada;
    private int tipoId;

    // Construtores
    // Construtor Cheio
    public Planta(int id, String raridade, int level, boolean premiada, int tipoId) {
        this.id = id;
        this.raridade = raridade;
        this.level = level;
        this.premiada = premiada;
        this.tipoId = tipoId;
    }

    // Construtor Vazio
    public Planta() {}

    // Métodos Acessores
    // Getters
    public int getId() { return id; }
    public String getRaridade() { return raridade; }
    public int getLevel() { return level; }
    public boolean isPremiada() { return premiada; }
    public int getTipoId() { return tipoId; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setRaridade(String raridade) { this.raridade = raridade; }
    public void setLevel(int level) { this.level = level; }
    public void setPremiada(boolean premiada) { this.premiada = premiada; }
    public void setTipoId(int tipo_id) { this.tipoId = tipo_id; }
}