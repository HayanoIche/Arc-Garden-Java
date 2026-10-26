package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class MissaoConcluida
{
    // Atributos
    private int id;
    private int usuarioId;
    private Missao missao;
    private LocalDate dataConclusao;
    private int pontosGanhos;

    // Construtores
    public MissaoConcluida() {}

    public MissaoConcluida(int id, int usuarioId, Missao missao, LocalDate dataConclusao, int pontosGanhos) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.missao = missao;
        this.dataConclusao = dataConclusao;
        this.pontosGanhos = pontosGanhos;
    }

    // Acessores
    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public Missao getMissao() { return missao; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public int getPontosGanhos() { return pontosGanhos; }

    public void setId(int id) { this.id = id; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setMissao(Missao missao) { this.missao = missao; }
    public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }
    public void setPontosGanhos(int pontosGanhos) { this.pontosGanhos = pontosGanhos; }
}
