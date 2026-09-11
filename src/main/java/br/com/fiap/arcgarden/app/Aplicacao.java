package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.*;
import br.com.fiap.arcgarden.repository.UsuarioRepository;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Aplicacao
{
    static private boolean rodando = true;
    static private String etapa = "inicio";

    public static void main(String[] args) throws Exception
    {
        Menu menu = new Menu();

        UsuarioRepository urep = new UsuarioRepository();

        // Instanciando os objetos
        Usuario usuario = new Usuario();

        while(rodando)
        {
            // ----------------------------------------------
            if (etapa == "inicio")
            {
                int resposta = menu.exibirEObterEscolha();

                switch (resposta)
                {
                    case -1: rodando = false; break;
                    case 1: etapa = "logar usuario"; break;
                    case 2: etapa = "cadastrar missão"; break;
                    case 3: etapa = "cadastrar usuario"; break;
                    case 4: etapa = "cadastrar planta"; break;
                    case 5: etapa = "informações do usuário"; break;
                    case 6: etapa = "ver missões"; break;
                    case 7: etapa = "comprar item"; break;
                    case 8: etapa = "ver plantas"; break;
                    case 9: etapa = "concluir missão"; break;
                    default: JOptionPane.showMessageDialog(null, resposta); break;
                }
            }

            // ----------------------------------------------
            if (etapa == "logar usuario")
            {
                String cpf = JOptionPane.showInputDialog("Digite seu CPF (XXXXXXXXXXX):");

                if (cpf != null)
                {
                    if (cpf.length() == 11)
                    {
                        JOptionPane.showMessageDialog(null, "Buscando no banco. . .");

                        Usuario user = urep.readByCpf(cpf);

                        if (user != null)
                        {
                            usuario = user;
                            JOptionPane.showMessageDialog(null, "Usuário encontrado e loguin efetuado!");
                            etapa = "inicio";
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro! cpf inválido!");
                    }
                } else {
                    etapa = "inicio";
                    JOptionPane.showMessageDialog(null, "Login cancelado.");
                }
            }

            // ----------------------------------------------
            if (etapa == "cadastrar usuario")
            {
                usuario = menu.exibirFormularioCadastroUsuario();

                if (usuario != null)
                {
                    urep.create(usuario);
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    etapa = "inicio";
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Cadastro falhou!");
                    etapa = "inicio";
                }
            }

            // ----------------------------------------------
            if (etapa == "informações do usuário")
            {
                if (usuario == null) { etapa = "inicio"; }

                if (usuario.getCpf() != null)
                {
                    JOptionPane.showMessageDialog(null, usuario);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Usuário não foi cadastrado");
                }

                etapa = "inicio";
            }
        }
    }
};
