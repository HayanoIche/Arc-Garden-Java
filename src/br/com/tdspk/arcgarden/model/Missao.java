package br.com.tdspk.arcgarden.model;

import java.time.LocalDate;

public class Missao
{
    // Atributos
    private String nome;
    private String dificuldade;
    private LocalDate dataDeConclusao;
    private int passos;

    // Construtores
    // Construtor vazio
    public Missao() {}

    // Construtor cheio
    public Missao(String nome, String dificuldade, LocalDate dataDeConclusao, int passos) {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.dataDeConclusao = dataDeConclusao;
        this.passos = passos;
    }

    // Métodos Acessores
    public String getNome() { return nome; }
    public String getDificuldade() { return dificuldade; }
    public LocalDate getDataDeConclusao() { return dataDeConclusao; }
    public int getPassos() { return passos; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public void setDataDeConclusao(LocalDate dataDeConclusao) { this.dataDeConclusao = dataDeConclusao; }
    public void setPassos(int passos) { this.passos = passos; }
}
