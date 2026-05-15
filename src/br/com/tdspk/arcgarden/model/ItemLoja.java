package br.com.tdspk.arcgarden.model;

public class ItemLoja
{
    // Atributos
    private String nome;
    private int preco;
    private boolean compravel;

    // Construtores
    // Construtor vazio
    public ItemLoja() {};

    // Construtor cheio
    public ItemLoja(String nome, int preco, boolean compravel) {
        this.nome = nome;
        this.preco = preco;
        this.compravel = compravel;
    }

    // Getters & Setters
    public String getNome() { return nome; }
    public int getPreco() { return preco; }
    public boolean isCompravel() { return compravel; }

    public void setPreco(int preco) { this.preco = preco; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCompravel(boolean compravel) { this.compravel = compravel; }

    // Metodo Worker
    public void efeito()
    {
        
    }
}
