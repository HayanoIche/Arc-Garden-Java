package br.com.tdspk.arcgarden.model;

import java.util.ArrayList;

public class Jardim
{
    // Atributos
    private String nome;
    private String tema;
    private ArrayList<Planta> plantas = new ArrayList<>();

    // Construtores
    // Construtor Vazio
    public Jardim() {}

    // Construtor Cheio
    public Jardim(String nome, String tema) {
        this.nome = nome;
        this.tema = tema;
    }

    // Métodos Acessores
    public String getNome() { return nome; }
    public String getTema() { return tema; }

    public void setNome(String nome) { this.nome = nome; }
    public void setTema(String tema) { this.tema = tema; }

    // Métodos Workers
    public void cadastrarPlanta(Planta planta)
    {
        plantas.add(planta);
    }

    public void mostrarPlantas()
    {
        for(int i = 0; i < plantas.size(); i += 1)
        {
            System.out.println(plantas.get(i));
        }
    }
}
