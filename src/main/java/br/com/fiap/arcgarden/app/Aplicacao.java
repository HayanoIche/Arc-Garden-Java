package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.*;
import br.com.fiap.arcgarden.repository.UsuarioRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Aplicacao
{
    static private String etapa = "inicio";

    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            if (etapa == "inicio")
            {
                    Menu.desenharMenuInicial();

                    //Scanner scan = new Scanner(System.in);
                    //String resposta = scan.nextLine();

                    //switch (resposta) {
                    //  default:
                    //      System.out.println("Opção inválida! programa terminado");
            }
        }

        //Usuario user = new Usuario();

        //user.setNome("Cleberon Matagal");
        //user.setCpf("11122233322");
        //user.setArcScore(10);
        //user.setStatus("ATIVO");

        //urep.create(user);
        //System.out.println(user);

        //UsuarioRepository urep = new UsuarioRepository();
        //List<Usuario> lista = urep.readByName("Cleberon Matagal");
        //System.out.println(lista);
        //for(Usuario user : lista) { urep.delete(user.getId()); }
    }
}