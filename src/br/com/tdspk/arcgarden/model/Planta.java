package br.com.tdspk.arcgarden.model;

public class Planta
{
    // Atributos
    private String nome;
    private String raridade;
    private int level;
    private int xp;
    private boolean premiada;
    private int preco;
    private String tipo;
    private int murchamento;
    private int valorRega;

    // Getters & Setters
    public String getNome()     { return nome; }
    public String getRaridade() { return raridade; }
    public int getLevel()       { return level; }
    public int getXp()          { return xp; }
    public boolean isPremiada() { return premiada; }
    public int getPreco()       { return preco; }
    public String getTipo()     { return tipo; }
    public int getMurchamento() { return murchamento; }
    public int getValorRega()   { return valorRega; }

    public void setNome(String nome)            { this.nome = nome; }
    public void setRaridade(String raridade)    { this.raridade = raridade; }
    public void setLevel(int level)             { this.level = level; }
    public void setXp(int xp)                   { this.xp = xp; }
    public void setPremiada(boolean premiada)   { this.premiada = premiada; }
    public void setPreco(int preco)             { this.preco = preco; }
    public void setTipo(String tipo)            { this.tipo = tipo; }
    public void setMurchamento(int murchamento) { this.murchamento = murchamento; }
    public void setValorRega(int valorRega)     { this.valorRega = valorRega; }

    // Métodos Workers
    public void regar()
    {

    }

    public void vender()
    {

    }
}
