package br.com.tdspk.arcgarden.model;

//
// Classe do jogador do ArcGarden
//

public class Jogador
{
    // Atributo
    private String nome;        // Nome do jogador
    private int agua;           // "Moeda" do jogo
    private int soulPoints;     // Pontos na soulUp
    private int missoesFeitas;  // Quantidade de Missões que o jogador concluiu

    // Getters & Setters
    public String getNome() { return nome; }
    public int getAgua() { return agua; }
    public int getSoulPoints() { return soulPoints; }
    public int getMissoesFeitas() { return missoesFeitas; }

    public void setNome(String nome) { this.nome = nome; }

    // Construtores
    // Construtor vazio
    public Jogador() {}

    // Construtor cheio
    public Jogador(String nome)
    {
        this.nome = nome;
        this.agua = 0;
        this.soulPoints = 0;
        this.missoesFeitas = 0;
    }

    // Metodos Workers
    public void gastarAgua()
    {

    }

    public void ganharAgua()
    {

    }

    public void gastarSoulPoints()
    {

    }

    public void ganharSoulPoints()
    {

    }

    public void aumentarMissoes()
    {

    }
}
