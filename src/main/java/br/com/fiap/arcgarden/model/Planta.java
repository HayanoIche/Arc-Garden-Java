package br.com.fiap.arcgarden.model;

public class Planta
{
    // Atributos
    private int id;
    private int jardimId;
    private TipoPlanta tipoPlanta;
    private String raridade;
    private String nivelPlanta;
    private String premiada;
    private int xpAtual;

    // Construtores
    public Planta() {}

    public Planta(int id, int jardimId, TipoPlanta tipoPlanta, String raridade, String nivelPlanta, String premiada, int xpAtual) {
        this.id = id;
        this.jardimId = jardimId;
        this.tipoPlanta = tipoPlanta;
        this.raridade = raridade;
        this.nivelPlanta = nivelPlanta;
        this.premiada = premiada;
        this.xpAtual = xpAtual;
    }

    // Acessores
    public int getId() { return id; }
    public int getJardimId() { return jardimId; }
    public TipoPlanta getTipoPlanta() { return tipoPlanta; }
    public String getRaridade() { return raridade; }
    public String getNivelPlanta() { return nivelPlanta; }
    public String getPremiada() { return premiada; }
    public int getXpAtual() { return xpAtual; }

    public void setId(int id) { this.id = id; }
    public void setJardimId(int jardimId) { this.jardimId = jardimId; }
    public void setTipoPlanta(TipoPlanta tipoPlanta) { this.tipoPlanta = tipoPlanta; }
    public void setRaridade(String raridade) { this.raridade = raridade; }
    public void setNivelPlanta(String nivelPlanta) { this.nivelPlanta = nivelPlanta; }
    public void setPremiada(String premiada) { this.premiada = premiada; }
    public void setXpAtual(int xpAtual) { this.xpAtual = xpAtual; }
}