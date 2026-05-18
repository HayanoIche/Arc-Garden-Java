package br.com.tdspk.arcgarden.app;

import br.com.tdspk.arcgarden.model.*;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Aplicacao
{
    public static void main(String[] args)
    {
        // Scanner
        Scanner scan = new Scanner(System.in);

        // Instanciando o ranking
        Ranking rank = new Ranking();

        // Inicializando Jogadores
        Jogador j1 = new Jogador("Kleber", "111.111.111-11", 170, 0);
        rank.adicionarJogadorAoMensal(j1);
        Jogador j2 = new Jogador("José", "222.222.222-22", 100, 0);
        rank.adicionarJogadorAoMensal(j2);
        Jogador j3 = new Jogador("Eric", "333.333.333-33", 75, 0);
        rank.adicionarJogadorAoMensal(j3);
        Jogador j4 = new Jogador("Ana", "444.444.444-44", 80, 0);
        rank.adicionarJogadorAoMensal(j4);
        Jogador j5 = new Jogador("Pedro", "555.555.555-55", 120, 0);
        rank.adicionarJogadorAoMensal(j5);

        // Instanciando Jogador
        Jogador j = new Jogador();
        rank.adicionarJogadorAoMensal(j);

        System.out.println("---------------------");
        System.out.println("      Arc Garden     ");
        System.out.println("---------------------");

        System.out.println("\n---- USUÁRIO ----");

        System.out.printf("Insira seu nome: ");
        j.setNome(scan.nextLine());

        System.out.printf("Insira seu cpf (xxx.xxx.xxx-xx): ");
        j.setCpf(scan.nextLine());

        System.out.printf("Digite a sua quantidade de ArcScore: ");
        j.setArcScore(scan.nextInt());

        System.out.printf("Digite a sua quantidade de SoulPoints: ");
        j.setSoulPoints(scan.nextInt());

        // Instanciando Missoes
        int quantidadeMissoes = 0;

        System.out.println("Quantas missões você fez esse mês?");

        quantidadeMissoes = scan.nextInt();
        scan.nextLine();

        if (quantidadeMissoes > 0)
        {
            System.out.println("------------------------");
            System.out.println("    CADASTRAR MISSÕES   ");
            System.out.println("------------------------");
        }

        for (int i = 0; i < quantidadeMissoes; i++)
        {
            System.out.println("Qual o título da " + (i+1) + "º missão (ex. Plante uma arvore)?");
            String nomeMissao = scan.nextLine();

            System.out.println("Qual a dificuldade da missão? (ex: fácil, difícil, médio)");
            String dificuldade = scan.nextLine();

            // --- SEPARAÇaO DA DATA ---
            System.out.println("Digite o DIA da conclusão: (dd)");
            int dia = scan.nextInt();

            System.out.println("Digite o MÊS da conclusão: (mm)");
            int mes = scan.nextInt();

            System.out.println("Digite o ANO da conclusão: (aaaa)");
            int ano = scan.nextInt();
            scan.nextLine();

            LocalDate dataDeConclusao = LocalDate.of(ano, mes, dia);

            System.out.println("Quantas vezes você concluiu a missão?");
            int vezes = scan.nextInt();

            scan.nextLine();

            Missao novaMissao = new Missao(nomeMissao, dificuldade, dataDeConclusao, vezes);
            j.adicionarMissao(novaMissao);
        }

        System.out.println("------------------------");
        System.out.println("    CADASTRAR JARDIM    ");
        System.out.println("------------------------");

        // Instanciando Jardim
        Jardim jardim = new Jardim();

        System.out.printf("Insira o nome do seu jardim: ");
        jardim.setNome(scan.nextLine());

        System.out.printf("Insira o tema do seu jardim (ex: Outono, Sakura, Inverno): ");
        jardim.setTema(scan.nextLine());

        System.out.println("Quantas plantas você têm no seu jardim?");

        // Instanciando Plantas
        int quantidadePlantas = scan.nextInt();
        scan.nextLine();

        if (quantidadePlantas > 0)
        {
            System.out.println("------------------------");
            System.out.println("    CADASTRAR PLANTAS   ");
            System.out.println("------------------------");
        }

        for (int i = 0; i < quantidadePlantas; i++)
        {
            System.out.println("\n--- Cadastrando a " + (i + 1) + "ª planta ---");

            System.out.println("Qual o nome da planta? ");
            String nome = scan.nextLine();

            System.out.println("Qual a raridade da planta? ");
            String raridade = scan.nextLine();

            System.out.println("Qual o level da planta? ");
            int level = scan.nextInt();
            scan.nextLine();

            System.out.println("A planta é brilhante? \n1 - Sim \n2 - Não");
            int opcaoPremiada = scan.nextInt();
            scan.nextLine();
            boolean premiada = (opcaoPremiada == 1);

            System.out.println("Qual a categoria da planta? ");
            String tipo = scan.nextLine();

            Planta novaPlanta = new Planta(nome, raridade, level, premiada, tipo);
            jardim.cadastrarPlanta(novaPlanta);
        }

        System.out.println("\nRANKING:");

        rank.mostrarRankingMensal();

        System.out.println("SEU JARDIM:");

        System.out.println(jardim);
        System.out.println();
        jardim.mostrarPlantas();
    }
}
