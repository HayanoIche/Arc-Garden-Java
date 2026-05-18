package br.com.tdspk.arcgarden.model;

import java.util.ArrayList;

public class Jogador
{
    // Atributos
    private String nome;
    private String cpf;
    private int arcScore;
    private int soulPoints;

    private ArrayList<Missao> missoes = new ArrayList<>();

    // toString

    // Construtores
    // Construtor Vazio
    public Jogador() {}

    // Construtor Cheio
    public Jogador(String nome, String cpf, int arcScore, int soulPoints) {
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.soulPoints = soulPoints;
    }

    // Métodos Acessores
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getArcScore() {return arcScore; }
    public int getSoulPoints() { return soulPoints; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setArcScore(int arcScore) { this.arcScore = arcScore; }
    public void setSoulPoints(int soulPoints) { this.soulPoints = soulPoints; }

    // Métodos Workers
    public void mostrarMissoes()
    {

    }
}
