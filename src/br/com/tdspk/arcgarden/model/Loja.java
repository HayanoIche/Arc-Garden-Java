package br.com.tdspk.arcgarden.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Loja
{
    // Atributos
    private ArrayList<ItemLoja> itensLoja = new ArrayList<>();
    private ArrayList<ItemLoja> itensRoleta = new ArrayList<>();

    private LocalDate dataAtualizacao;

    // Construtores
    // Construtor vazio
    public Loja() {}

    // Construtor cheio
    public Loja(ArrayList<ItemLoja> itensLoja, ArrayList<ItemLoja> itensRoleta, LocalDate dataAtualizacao) {
        this.itensLoja = itensLoja;
        this.itensRoleta = itensRoleta;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Métodos Workers
    public boolean comprar(ItemLoja item)
    {
        return true;
    }

    public void converterSoulPoints()
    {

    }

    public void atualizar()
    {

    }
}
