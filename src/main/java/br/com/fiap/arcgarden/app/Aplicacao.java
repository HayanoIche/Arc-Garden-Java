package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.*;
import br.com.fiap.arcgarden.repository.MissaoRepository;
import br.com.fiap.arcgarden.repository.PlantaRepository;
import br.com.fiap.arcgarden.repository.UsuarioRepository;

import javax.swing.*;
import java.util.List;

public class Aplicacao
{
    static private boolean rodando = true;
    static private String etapa = "inicio";

    public static void main(String[] args) throws Exception
    {
        // Menu
        Menu menu = new Menu();

        // Repositories
        UsuarioRepository urep = new UsuarioRepository();
        PlantaRepository  prep = new PlantaRepository();
        MissaoRepository  mrep = new MissaoRepository();

        // Usuario do sistema
        Usuario usuario = new Usuario();

        // Programa principal
        while(rodando)
        {
            // ----------------------------------------------
            if (etapa == "inicio")
            {
                int resposta = menu.exibirMenuInicial();

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
                String cpf = JOptionPane.showInputDialog(null, "Digite seu CPF (XXX.XXX.XXX-XX):", "Login do usuário", JOptionPane.QUESTION_MESSAGE);

                if (cpf != null)
                {
                    if (cpf.length() == 14)
                    {
                        Usuario user = urep.readByCpf(cpf);

                        if (user != null)
                        {
                            usuario = user;
                            JOptionPane.showMessageDialog(null, "Usuário encontrado e login efetuado!");
                            etapa = "inicio";
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Usuário não encontrado");
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
            if (etapa.equals("informações do usuário"))
            {
                if (usuario == null || usuario.getCpf() == null)
                {
                    JOptionPane.showMessageDialog(null, "Nenhum usuário está logado atualmente!");
                }
                else
                {
                    // Busca do banco para garantir que o usuário venha com as listas de
                    // itensComprados e missoesConcluidas devidamente preenchidas
                    Usuario usuarioAtualizado = urep.read(usuario.getId());

                    if (usuarioAtualizado != null)
                    {
                        usuario = usuarioAtualizado;
                        JOptionPane.showMessageDialog(null, usuario);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Erro ao carregar dados atualizados do usuário.");
                    }
                }

                etapa = "inicio";
            }

            // ----------------------------------------------
            if (etapa == "concluir missão")
            {
                if (usuario == null || usuario.getCpf() == null)
                {
                    JOptionPane.showMessageDialog(null, "Você precisa estar logado para concluir uma missão!");
                    etapa = "inicio";
                }
                else
                {
                    String idMissaoStr = JOptionPane.showInputDialog("Digite o ID da missão a ser concluída:");
                    String pontosStr = JOptionPane.showInputDialog("Digite a quantidade de pontos ganhos:");

                    if (idMissaoStr != null && pontosStr != null)
                    {
                        try {
                            int idMissao = Integer.parseInt(idMissaoStr);
                            int pontosGanhos = Integer.parseInt(pontosStr);

                            urep.concluirMissao(usuario.getId(), idMissao, pontosGanhos);
                            JOptionPane.showMessageDialog(null, "Missão concluída com sucesso!");

                            // Atualiza a instância do usuário na memória
                            Usuario userAtualizado = urep.read(usuario.getId());
                            if (userAtualizado != null) {
                                usuario = userAtualizado;
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID ou Pontos inválidos!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao concluir missão: " + e.getMessage());
                        }
                    }
                    etapa = "inicio";
                }
            }

            // ----------------------------------------------
            if (etapa == "comprar item")
            {
                if (usuario == null || usuario.getCpf() == null)
                {
                    JOptionPane.showMessageDialog(null, "Você precisa estar logado para comprar um item!");
                    etapa = "inicio";
                }
                else
                {
                    String idItemStr = JOptionPane.showInputDialog("Digite o ID do item que deseja comprar:");
                    if (idItemStr == null) { etapa = "inicio"; continue; }
                    else {
                        if (idItemStr.strip().isEmpty()) { etapa = "inicio"; continue; }
                    }

                    String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade:");
                    if (quantidadeStr == null) { etapa = "inicio"; continue; }
                    else {
                        if (quantidadeStr.strip().isEmpty()) { etapa = "inicio"; continue; }
                    }

                    if (idItemStr != null && quantidadeStr != null)
                    {
                        try {
                            int idItem = Integer.parseInt(idItemStr);
                            int quantidade = Integer.parseInt(quantidadeStr);

                            urep.registrarCompra(usuario.getId(), idItem, quantidade);
                            JOptionPane.showMessageDialog(null, "Item comprado com sucesso!");

                            // Atualiza a instância do usuário na memória com a nova compra
                            Usuario userAtualizado = urep.read(usuario.getId());
                            if (userAtualizado != null) {
                                usuario = userAtualizado;
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "ID ou Quantidade inválida!");
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao registrar compra: " + e.getMessage());
                        }
                    }
                    etapa = "inicio";
                }
            }

            // ----------------------------------------------
            if (etapa == "cadastrar planta")
            {
                Planta planta = menu.exibirFormularioCadastroPlanta();

                if (planta != null)
                {
                    prep.create(planta);
                    JOptionPane.showMessageDialog(null, "Planta cadastrada com sucesso!\nID da planta: " + planta.getId());
                    etapa = "inicio";
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Cadastro de planta cancelado!");
                    etapa = "inicio";
                }
            }

            // ----------------------------------------------
            if (etapa == "cadastrar missão")
            {
                Missao missao = menu.exibirFormularioCadastroMissao();

                if (missao != null)
                {
                    mrep.create(missao);
                    JOptionPane.showMessageDialog(null, "Missão cadastrada com sucesso!\nID da missão: " + missao.getId());
                    etapa = "inicio";
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Cadastro de missão cancelado!");
                    etapa = "inicio";
                }
            }

            // ----------------------------------------------
            if (etapa == "ver plantas")
            {
                try {
                    List<Planta> listaPlantas = prep.readAll();

                    if (listaPlantas.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Nenhuma planta encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        etapa = "inicio";
                    }

                    for (Planta planta : listaPlantas)
                    {
                        String info = "ID: " + planta.getId() + "\n" +
                                "Nome: " + planta.getNome() + "\n" +
                                "Categoria: " + planta.getCategoria() + "\n" +
                                "XP Máximo: " + planta.getXpMaximo() + "\n" +
                                "Descrição: " + planta.getDescricao();

                        JOptionPane.showMessageDialog(null, info, "Detalhes da Planta", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar as plantas: " + e.getMessage());
                }

                etapa = "inicio";
            }

            // ----------------------------------------------
            if (etapa == "ver missões")
            {
                try {
                    List<Missao> listaMissao = mrep.readAll();

                    if (listaMissao.isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Nenhuma missão encontrada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        etapa = "inicio";
                    }

                    for (Missao missao : listaMissao)
                    {
                        // missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos
                        String info = "ID: " + missao.getId() + "\n" +
                                "Nome: " + missao.getNome() + "\n" +
                                "Descricao: " + missao.getDescricao() + "\n" +
                                "Dificuldade: " + missao.getDificuldade() + "\n" +
                                "Vezes: " + missao.getVezes() + "\n" +
                                "Recompensa Pontos" + missao.getRecompensaPontos();

                        JOptionPane.showMessageDialog(null, info, "Detalhes da missão", JOptionPane.INFORMATION_MESSAGE);
                        etapa = "inicio";
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar as missões" + e.getMessage());
                    etapa = "inicio";
                }
            }


        }
    }
};
