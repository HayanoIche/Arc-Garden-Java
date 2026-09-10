package br.com.fiap.arcgarden.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Ranking
{
    // Atributos
    private ArrayList<Usuario> rankingMensal = new ArrayList<>();
    private ArrayList<Usuario> rankingGlobal = new ArrayList<>();

    // Construtor vazio
    public Ranking() {}

    // Metodo para ordenar os jogadores do maior score para o menor
    public void calcularRankingMensal()
    {
        rankingMensal.sort(Comparator.comparingInt(Usuario::getArcScore).reversed());
    }

    public void mostrarRankingMensal()
    {
        calcularRankingMensal();

        System.out.println("\n---------------------------------------");
        System.out.println("       RANKING MENSAL - ARC GARDEN      ");
        System.out.println("---------------------------------------");
        System.out.printf("%-6s | %-15s | %-10s%n", "POS", "JOGADOR", "ARC SCORE");
        System.out.println("----------------------------------------");

        for (int i = 0; i < rankingMensal.size(); i++) {
            Usuario j = rankingMensal.get(i);

            int posicao = i + 1;

            String posFormatada = posicao + "º";
            if (posicao == 1) posFormatada = "🥇 1º";
            if (posicao == 2) posFormatada = "🥈 2º";
            if (posicao == 3) posFormatada = "🥉 3º";

            System.out.printf("%-6s | %-15s | %-10d%n", posFormatada, j.getNome(), j.getArcScore());
        }
        System.out.println("---------------------------------------\n");
    }

    public void adicionarJogadorAoMensal(Usuario user)
    {
        this.rankingMensal.add(user);
    }
}
