package br.com.fiap.arcgarden.model;

import java.util.ArrayList;

public class Usuario
{
    private int id;
    private String nome;
    private String cpf;
    private int arcScore;
    private String status;

    private ArrayList<MissaoConcluida> missoesConcluidas = new ArrayList<>();
    private ArrayList<ItemLoja> itensComprados = new ArrayList<>();

    // Construtores
    public Usuario(String nome, String cpf, int arcScore, String status)
    {
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.status = status;
    }

    public Usuario(int id, String nome, String cpf, int arcScore, String status)
    {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.arcScore = arcScore;
        this.status = status;
    }

    public Usuario() {}

    // Métodos Acessores
    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getArcScore() { return arcScore; }
    public String getStatus() { return status; }
    public ArrayList<MissaoConcluida> getMissoesConcluidas() { return missoesConcluidas; }
    public ArrayList<ItemLoja> getItensComprados() { return itensComprados; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setArcScore(int arcScore) { this.arcScore = arcScore; }
    public void setStatus(String status) { this.status = status; }
    public void setMissoesConcluidas(ArrayList<MissaoConcluida> missoesConcluidas) { this.missoesConcluidas = missoesConcluidas; }
    public void setItensComprados(ArrayList<ItemLoja> itensComprados) { this.itensComprados = itensComprados; }

    // Métodos Workers
    public void adicionarMissaoConcluida(MissaoConcluida missao)
    {
        this.missoesConcluidas.add(missao);
    }

    public void adicionarItemComprado(ItemLoja item)
    {
        this.itensComprados.add(item);
    }

    // ToString
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------\n");
        sb.append("            DADOS DO JOGADOR             \n");
        sb.append("-----------------------------------------\n");
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
                MissaoConcluida m = missoesConcluidas.get(i);
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