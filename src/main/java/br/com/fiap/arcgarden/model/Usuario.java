package br.com.fiap.arcgarden.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario
{
    // Atributos
    private int id;
    private String nome;
    private String cpf;
    private int arcScore;
    private String status;
    private String email;
    private String senha;

    private List<ItemComprado> itensComprados = new ArrayList<>();
    private List<MissaoConcluida> missoesConcluidas = new ArrayList<>();

    // Construtores
    public Usuario() {}

    public Usuario(int id, String nome, String cpf, Integer arcScore, String status, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.status = status;
        this.email = email;
        this.senha = senha;
    }

    // Acessores
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Integer getArcScore() { return arcScore; }
    public String getStatus() { return status; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public List<ItemComprado> getItensComprados() { return itensComprados; }
    public List<MissaoConcluida> getMissoesConcluidas() { return missoesConcluidas; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setArcScore(Integer arcScore) { this.arcScore = arcScore; }
    public void setStatus(String status) { this.status = status; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setItensComprados(List<ItemComprado> itensComprados) { this.itensComprados = itensComprados; }
    public void setMissoesConcluidas(List<MissaoConcluida> missoesConcluidas) { this.missoesConcluidas = missoesConcluidas; }

    public void adicionarItemComprado(ItemComprado item)
    {
        if (this.itensComprados == null) { this.itensComprados = new ArrayList<>(); }
        this.itensComprados.add(item);
    }

    public void adicionarMissaoConcluida(MissaoConcluida missaoConcluida)
    {
        if (this.missoesConcluidas == null) { this.missoesConcluidas = new ArrayList<>(); }
        this.missoesConcluidas.add(missaoConcluida);
    }
}