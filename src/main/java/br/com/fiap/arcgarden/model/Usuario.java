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
    private ArrayList<ItemLoja> itensComprados = new ArrayList<>();

    // Construtores
    // Construtor Cheio
    public Usuario(String nome, String cpf, int arcScore, String status)
    {
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.status = status;
    }

    // Construtor Completo com ID
    public Usuario(int id, String nome, String cpf, int arcScore, String status)
    {
        this.id = id;
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
    public ArrayList<Missao> getMissoesConcluidas() { return missoesConcluidas; }
    public ArrayList<ItemLoja> getItensComprados() { return itensComprados; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setArcScore(int arcScore) { this.arcScore = arcScore; }
    public void setStatus(String status) { this.status = status; }
    public void setMissoesConcluidas(ArrayList<Missao> missoesConcluidas) { this.missoesConcluidas = missoesConcluidas; }
    public void setItensComprados(ArrayList<ItemLoja> itensComprados) { this.itensComprados = itensComprados; }

    // Métodos Workers

    // Adiciona uma missão à lista do usuário
    public void adicionarMissaoConcluida(Missao missao)
    {
        this.missoesConcluidas.add(missao);
    }

    // Adiciona um item à lista do usuário
    public void adicionarItemComprado(ItemLoja item)
    {
        this.itensComprados.add(item);
    }

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

    // Função pra printar na tela todos os itens comprados
    public void mostrarItensComprados()
    {
        System.out.println("\n--- Itens de: " + this.nome + " ---");
        if (itensComprados.isEmpty()) {
            System.out.println("Nenhum item comprado por este jogador.");
        } else {
            for (int i = 0; i < itensComprados.size(); i++) {
                ItemLoja item = itensComprados.get(i);
                System.out.println((i + 1) + ". " + item.getNome() + " [" + item.getTipo() + "] - Preço: " + item.getPrecoAgua());
            }
        }
        System.out.println("---------------------------------------");
    }

    // ToString
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("            DADOS DO JOGADOR             \n");
        sb.append("=========================================\n");
        sb.append("ID.................: ").append(id).append("\n");
        sb.append("Nome...............: ").append(nome).append("\n");
        sb.append("CPF................: ").append(cpf).append("\n");
        sb.append("ArcScore...........: ").append(arcScore).append(" pts\n");
        sb.append("Status.............: ").append(status).append("\n");

        sb.append("\n--- ITENS COMPRADOS (").append(itensComprados.size()).append(") ---\n");
        if (itensComprados.isEmpty()) {
            sb.append("Nenhum item comprado.\n");
        } else {
            for (int i = 0; i < itensComprados.size(); i++) {
                ItemLoja item = itensComprados.get(i);
                sb.append(i + 1).append(". ")
                        .append(item.getNome() != null ? item.getNome() : "Item #" + item.getId())
                        .append(" | Tipo: ").append(item.getTipo())
                        .append(" | Preço: ").append(item.getPrecoAgua()).append(" Águas\n");
            }
        }

        sb.append("\n--- MISSÕES CONCLUÍDAS (").append(missoesConcluidas.size()).append(") ---\n");
        if (missoesConcluidas.isEmpty()) {
            sb.append("Nenhuma missão concluída.\n");
        } else {
            for (int i = 0; i < missoesConcluidas.size(); i++) {
                Missao m = missoesConcluidas.get(i);
                sb.append(i + 1).append(". ")
                        .append(m.getNome() != null ? m.getNome() : "Missão #" + m.getId())
                        .append(" [").append(m.getDificuldade()).append("]")
                        .append(" - Concluída em: ").append(m.getDataDeConclusao()).append("\n");
            }
        }
        sb.append("=========================================");

        return sb.toString();
    }
}