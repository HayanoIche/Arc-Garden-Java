package br.com.fiap.arcgarden.model;

import java.time.LocalDate;

public class MissaoConcluida extends Missao
{
    private LocalDate dataDeConclusao;

    // Construtores
    public MissaoConcluida(Missao m, LocalDate dataDeConclusao)
    {
        super(m.getNome(), m.getDescricao(), m.getDificuldade(), m.getVezes(), m.getRecompensaPontos());
        this.dataDeConclusao = dataDeConclusao;
    }

    public MissaoConcluida() {}

    // Acessores
    public LocalDate getDataDeConclusao() { return dataDeConclusao; }
    public void setDataDeConclusao(LocalDate dataDeConclusao) { this.dataDeConclusao = dataDeConclusao; }
}
