package br.com.tdspk.arcgarden.model;

import java.time.LocalDate;

public class Missao
{
    // Atributos
    private String nome;
    private String dificuldade;
    private LocalDate dataDeConclusão;
    private String tipo;
    private int passos;

    // Construtores
    // Construtor vazio
    public Missao() {}

    // Construtor cheio
    public Missao(String nome, String dificuldade, LocalDate dataDeConclusão, String tipo, int passos) {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.dataDeConclusão = dataDeConclusão;
        this.tipo = tipo;
        this.passos = passos;
    }

    // Métodos Acessores
    public String getNome() { return nome; }
    public String getDificuldade() { return dificuldade; }
    public LocalDate getDataDeConclusão() { return dataDeConclusão; }
    public String getTipo() { return tipo; }
    public int getPassos() { return passos; }

    public void setNome(String nome) { this.nome = nome; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public void setDataDeConclusão(LocalDate dataDeConclusão) { this.dataDeConclusão = dataDeConclusão; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPassos(int passos) { this.passos = passos; }
}
