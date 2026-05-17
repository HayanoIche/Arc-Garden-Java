package br.com.tdspk.arcgarden.model;

import java.util.ArrayList;

//
// Classe que mostra o ranking
//

public class Ranking
{
    // Atributos
    private ArrayList<Jogador> rankingMensal = new ArrayList<>();
    private ArrayList<Jogador> rankingGlobal = new ArrayList<>();

    // Construtores
    public Ranking() {}

    // Getters & Setters
    public ArrayList<Jogador> getRankingMensal() { return rankingMensal; }
    public ArrayList<Jogador> getRankingGlobal() { return rankingGlobal; }

    // Métodos Workers
    public void calcularRankingMensal()
    {

    }

    public void calcularRankingGlobal()
    {

    }

    public void recompensarJogadores()
    {

    }
}
