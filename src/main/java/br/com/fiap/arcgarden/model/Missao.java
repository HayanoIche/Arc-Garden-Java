package br.com.fiap.arcgarden.model;

public class Missao
{
    private int id;
    private String nome;
    private String descricao;
    private String dificuldade;
    private int vezes;
    private int recompensaPontos;

    public Missao() {}

    public Missao(String nome, String descricao, String dificuldade, int vezes, int recompensaPontos)
    {
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.vezes = vezes;
        this.recompensaPontos = recompensaPontos;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDificuldade() { return dificuldade; }
    public int getVezes() { return vezes; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }

    public void setVezes(int vezes) { this.vezes = vezes; }

    public int getRecompensaPontos() { return recompensaPontos; }
    public void setRecompensaPontos(int recompensaPontos) { this.recompensaPontos = recompensaPontos; }

    @Override
    public String toString() {
        return nome + " [" + dificuldade + "] - Recompensa: " + recompensaPontos + " pts";
    }
}