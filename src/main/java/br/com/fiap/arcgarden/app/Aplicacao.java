package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.*;
import br.com.fiap.arcgarden.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Scanner;

public class Aplicacao
{
    public static void main(String[] args) throws Exception {
        Usuario user = new Usuario();

        user.setNome("Cleberon Matagal");
        user.setCpf("11122233322");
        user.setArcScore(10);
        user.setStatus("ATIVO");

        UsuarioRepository urep = new UsuarioRepository();
        urep.create(user);

        System.out.println(user);
    }
}