package br.com.fiap.arcgarden.model;

import java.util.ArrayList;

public class Usuario
{
    // Atributos
    private int id;
    private String nome;
    private String cpf;
    private int arcScore;
    private String status;

    private ArrayList<Missao> missoesConcluidas = new ArrayList<>();
    private ArrayList<ItemLoja> itensComprados  = new ArrayList<>();

    // Construtores
    // Construtor Cheio
    public Usuario(String nome, String cpf, int arcScore, String status) {
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.status = status;
    }

    // Construtor Vazio
    public Usuario() {}

    // Métodos Acessores
    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getArcScore() { return arcScore; }
    public String getStatus() { return status; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setArcScore(int arcScore) { this.arcScore = arcScore; }
    public void setStatus(String status) { this.status = status; }

    // Métodos Workers

    // Função pra printar na tela todas as missões
    public void mostrarMissoesConcluidas()
    {
        System.out.println("\n--- Missões do Jogador: " + this.nome + " ---");
        if (missoesConcluidas.isEmpty()) {
            System.out.println("Nenhuma missão cadastrada para este jogador.");
        } else {
            for (int i = 0; i < missoesConcluidas.size(); i++) {
                Missao m = missoesConcluidas.get(i);
                System.out.println((i + 1) + ". " + m.getNome() + " [" + m.getDificuldade() + "] - Concluída em: " + m.getDataDeConclusao());
            }
        }
        System.out.println("---------------------------------------");
    }

    public void adicionarMissaoConcluida(Missao missao) {
        this.missoesConcluidas.add(missao);
    }
}
