package br.com.fiap.arcgarden.model;

public class Missao
{
    // Atributos
    private int id;
    private String nome;
    private String descricao;
    private String dificuldade;
    private int vezes;
    private int recompensaPontos;

    // Construtores
    public Missao() {}

    public Missao(int id, String nome, String descricao, String dificuldade, int vezes, int recompensaPontos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.vezes = vezes;
        this.recompensaPontos = recompensaPontos;
    }
    
    // Acessores
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDificuldade() { return dificuldade; }
    public int getVezes() { return vezes; }
    public int getRecompensaPontos() { return recompensaPontos; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }
    public void setVezes(int vezes) { this.vezes = vezes; }
    public void setRecompensaPontos(int recompensaPontos) { this.recompensaPontos = recompensaPontos; }
}