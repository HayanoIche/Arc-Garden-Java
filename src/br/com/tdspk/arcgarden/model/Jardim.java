package br.com.tdspk.arcgarden.model;

import java.time.LocalDate;

//
// Classe que maneja o jardim do jogo
//

public class Jardim
{
    // Atributos
    private String nome;
    private int tema;
    private LocalDate dataAtualizacao;

    private Jogador dono;

    // Construtores
    public Jardim(String nome)
    {
        this.nome = nome;
        tema = 0;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getTema() { return tema; }

    public void setNome(String nome) { this.nome = nome; }
    public void setTema(int tema) { this.tema = tema; }

    // Metódos Workers
    // Metodo para adicionar uma planta nova ao jardim
    public void ganharPlanta(Planta planta)
    {

    }

    // Metodo para mostrar todas as plantas que estão no jardim
    public void mostrarPlantas()
    {

    }

    // Metodo que atualiza as plantas sempre que o jogador fica muito tempo sem regar
    public void atualizarPlantas()
    {

    }

    // Metodo usado pra calcular o score total do jardim pra saber o ranking global dele
    public void calcularScoreTotal()
    {

    }

    // Metodo que roda a parte principal que é a "gameplay" do jardim
    public void Jogar()
    {

    }
}
