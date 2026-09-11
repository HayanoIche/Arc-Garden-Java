package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class Missao
{
    private int id;
    private String nome;
    private String descricao;
    private String dificuldade; // 'F', 'M', 'D'
    private int vezes;
    private int recompensaPontos;
    private LocalDate dataDeConclusao; // Campo auxiliar para quando a missão estiver na lista do jogador

    public Missao() {}

    public Missao(String nome, String descricao, String dificuldade, int vezes, int recompensaPontos) {
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.vezes = vezes;
        this.recompensaPontos = recompensaPontos;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }

    public int getVezes() { return vezes; }
    public void setVezes(int vezes) { this.vezes = vezes; }

    public int getRecompensaPontos() { return recompensaPontos; }
    public void setRecompensaPontos(int recompensaPontos) { this.recompensaPontos = recompensaPontos; }

    public LocalDate getDataDeConclusao() { return dataDeConclusao; }
    public void setDataDeConclusao(LocalDate dataDeConclusao) { this.dataDeConclusao = dataDeConclusao; }

    @Override
    public String toString() {
        return nome + " [" + dificuldade + "] - Recompensa: " + recompensaPontos + " pts";
    }
}