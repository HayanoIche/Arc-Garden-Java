package br.com.fiap.arcgarden.model;

import java.util.ArrayList;
import java.util.List;

public class Jardim
{
    // Atributos
    private int id;
    private String nome;
    private String tema;
    private int usuarioId;

    private List<Planta> plantas = new ArrayList<>();

    // Construtores
    public Jardim() {}

    public Jardim(int id, String nome, String tema, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.tema = tema;
        this.usuarioId = usuarioId;
    }

    public Jardim(int id, String nome, String tema, int usuarioId, List<Planta> plantas) {
        this.id = id;
        this.nome = nome;
        this.tema = tema;
        this.usuarioId = usuarioId;
        this.plantas = plantas;
    }

    // Acessores
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTema() { return tema; }
    public int getUsuarioId() { return usuarioId; }
    public List<Planta> getPlantas() { return plantas; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTema(String tema) { this.tema = tema; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setPlantas(List<Planta> plantas) { this.plantas = plantas; }

    public void adicionarPlanta(Planta planta)
    {
        if (this.plantas == null) { this.plantas = new ArrayList<>(); }
        this.plantas.add(planta);
    }
}