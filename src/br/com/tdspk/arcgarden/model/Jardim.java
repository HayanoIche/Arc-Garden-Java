package br.com.tdspk.arcgarden.model;

import java.time.LocalDate;

public class Jardim
{
    // Atributos
    private String nome;
    private int tema;
    private LocalDate dataAtualizacao;

    // Construtores
    public Jardim(String nome)
    {
        this.nome = nome;
        tema = 0;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getTema() { return tema; }

    public void setNome(String nome) { this.nome = nome; }
    public void setTema(int tema) { this.tema = tema; }

    // Metódos Workers
    public void ganharPlanta(Planta planta)
    {

    }
}
