package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.Usuario;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class Menu
{
    private int opcaoSelecionada = -1;

    public int exibirEObterEscolha()
    {
        opcaoSelecionada = -1;

        // Configurações do painel
        // Cores
        Color corDeFundo = new Color(255, 252, 247);
        UIManager.put("OptionPane.background", corDeFundo);
        UIManager.put("Panel.background", corDeFundo);
        // Titulo
        String tituloHtml = "<html><center><h2 style='color: #2e7d32; margin:0;'>A R C  G A R D E N</h2>" +
                "<hr><p>O que deseja fazer?</p></center></html>";

        // Painel
        JPanel painel = new JPanel(new BorderLayout(0, 15));

        painel.add(new JLabel(tituloHtml, SwingConstants.CENTER), BorderLayout.NORTH);

        // Grade de opções
        Object[][] opcoes = {
                {"Entrar com usuário já cadastrado", 1  },
                {"Cadastrar missão no sistema", 2      },
                {"Entrar como novo usuário", 3          },
                {"Cadastrar planta no sistema", 4      },
                {"Ver informações do usuário", 5        },
                {"Ver todas as missões cadastradas", 6  },
                {"Comprar item na loja", 7              },
                {"Ver todas as plantas cadastradas", 8  },
                {"Concluir uma missão", 9               },
        };

        painel.add(criarGridDeBotoes(opcoes,0, 2, 10, 10), BorderLayout.CENTER);

        // Exibe o diálogo
        JOptionPane.showOptionDialog(
                null,
                painel,
                "Arc Garden - Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Sair"},
                null
        );

        return opcaoSelecionada;
    }

    private JPanel criarGridDeBotoes(Object[][] opcoes, int w, int h, int gw, int gh)
    {
        JPanel painel = new JPanel(new GridLayout(w, h, gw, gh));

        for (Object[] op : opcoes) {
            String texto = (String) op[0];
            int id = (int) op[1];

            JButton btn = new JButton(texto);
            btn.setFocusable(false);

            btn.addActionListener(e -> {
                this.opcaoSelecionada = id;
                // Fecha a janela atual do JOptionPane
                Component comp = (Component) e.getSource();
                JOptionPane.getRootFrame().dispose();
            });

            painel.add(btn);
        }

        return painel;
    }

    public static Usuario exibirFormularioCadastroUsuario()
    {
        JTextField campo1 = new JTextField();
        JTextField campo2 = new JTextField();
        JTextField campo3 = new JTextField();

        JPanel painel = new JPanel(new GridLayout(3, 2, 5, 5));

        painel.add(new JLabel("Nome: "));
        painel.add(campo1);
        painel.add(new JLabel("Cpf: "));
        painel.add(campo2);
        painel.add(new JLabel("ArcScore: "));
        painel.add(campo3);

        int opcao = JOptionPane.showConfirmDialog(
                null,
                painel,
                "Preencha o Formuário",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // 4. Capturar as respostas se o usuário clicar em OK
        if (opcao == JOptionPane.OK_OPTION) {
            String valor1 = campo1.getText();
            String valor2 = campo2.getText();
            int valor3 = Integer.parseInt(campo3.getText());

            return new Usuario(valor1, valor2, valor3, "ATIVO");
        }

        return null;
    }

    public static Planta exibirFormularioCadastroPlanta()
    {
        while(true)
        {
            // nome, categoria, xp_maximo, descricao
            JTextField campo1 = new JTextField();
            JTextField campo3 = new JTextField();
            JTextField campo4 = new JTextField();

            JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));

            // Caixa de seleção de categoria
            String[] opcoes = {"AGUA", "ENERGIA", "RENOVACAO", "NATUREZA"};
            JComboBox<String> caixaCategorias = new JComboBox<>(opcoes);
            caixaCategorias.setSelectedIndex(-1);

            painel.add(new JLabel("Nome: "));
            painel.add(campo1);
            painel.add(new JLabel("Categoria: "));
            painel.add(caixaCategorias);
            painel.add(new JLabel("Xp Máximo: "));
            painel.add(campo3);
            painel.add(new JLabel("Descricao: "));
            painel.add(campo4);

            int opcao = JOptionPane.showConfirmDialog(
                    null,
                    painel,
                    "Cadastro de planta",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            // Capturar as respostas
            int valor3 = 0;

            if (opcao == JOptionPane.OK_OPTION) {
                String valor1 = campo1.getText();
                String valor2 = "";
                String valor4 = campo4.getText();

                if (valor1 == null || valor1.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! campo 1 vazio");
                    continue;
                }

                if (valor4 == null || valor4.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! campo 4 vazio");
                    continue;
                }

                try {
                    valor3 = Integer.parseInt(campo3.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! xp máximo não numerico");
                    continue;
                }

                int valor2Index = caixaCategorias.getSelectedIndex();

                if (valor2Index != -1) {
                    valor2 = opcoes[valor2Index];
                }

                return new Planta(valor1, valor2, valor3, valor4);
            }
            return null;
        }
    }

    /*
        CREATE TABLE tb_missoes (
        missao_id INTEGER GENERATED ALWAYS AS IDENTITY,
        nome VARCHAR2(200) NOT NULL,
        descricao VARCHAR2(500) NOT NULL,
        dificuldade VARCHAR2(1) NOT NULL,
        vezes INTEGER NOT NULL,
        recompensa_pontos INTEGER NOT NULL,

        CONSTRAINT tb_missoes_pk PRIMARY KEY (missao_id),

        CONSTRAINT tb_missoes_ck
            CHECK (
                dificuldade IN ('F', 'M', 'D')
                    AND vezes > 0
                    AND recompensa_pontos >= 0
                    AND recompensa_pontos <= 100
                )
        );
     */
    public static Missao exibirFormularioCadastroMissao()
    {
        while(true)
        {
            JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));

            // nome, descricao, dificulade, vezes recompensa_pontos
            JTextField campo1 = new JTextField(); // Nome
            JTextField campo2 = new JTextField(); // Descricao
            JTextField campo4 = new JTextField(); // Vezes
            JTextField campo5 = new JTextField(); // Recompensa_pontos

            // Caixa de seleção de dificuldade
            String[] opcoes = {"F", "M", "D"};
            JComboBox<String> caixaDificuldades = new JComboBox<>(opcoes);
            caixaDificuldades.setSelectedIndex(-1);

            painel.add(new JLabel("Nome: "));
            painel.add(campo1);
            painel.add(new JLabel("Descricao: "));
            painel.add(campo2);
            painel.add(new JLabel("Dificuldade: "));
            painel.add(caixaDificuldades);
            painel.add(new JLabel("Vezes: "));
            painel.add(campo4);
            painel.add(new JLabel("Recompensa em pontos: "));
            painel.add(campo5);

            int opcao = JOptionPane.showConfirmDialog(
                    null,
                    painel,
                    "Cadastro de missão",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            // Capturar as respostas
            if (opcao == JOptionPane.OK_OPTION)
            {
                String valor1 = campo1.getText();
                String valor2 = campo2.getText();
                String valor3 = "";
                int valor4 = 0;
                int valor5 = 0;

                if (valor1 == null || valor1.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! campo 1 vazio");
                    continue;
                }

                if (valor2 == null || valor2.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! campo 4 vazio");
                    continue;
                }

                int valor3Index = caixaDificuldades.getSelectedIndex();

                if (valor3Index != -1) {
                    valor3 = opcoes[valor3Index];
                }

                try {
                    valor4 = Integer.parseInt(campo4.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! Vezes não numerico");
                    continue;
                }

                try {
                    valor5 = Integer.parseInt(campo5.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Erro no cadastro! Recompensa em pontos não numerico");
                    continue;
                }

                return new Missao(valor1, valor2, valor3, valor4, valor5);
            }
            return null;
        }
    }
}

/*import javax.swing.*;
import java.awt.*;

public class Menu
{
    public static void limparConsole()
    {
        try
        {
            String system = System.getProperty("os.name");
            if (system.contains("Windows"))
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception e) {}
    }

    public static void desenharMenuInicial()
    {

        limparConsole();

        String menu = """
\nA R C  G A R D E N
----------------------
\nO que deseja fazer?
\n - Usuário -\n
1 - Entrar com usuário já cadastrado

2 - Entrar como novo usuário
3 - Ver informações do usuário
4 - Comprar item na loja
5 - Concluir uma missão
\n - Sistema -\n
6 - Cadastrar missões no sistema
7 - Ver todas as missões cadastradas
8 - Cadastrar plantas no sistema
9 - Ver todas as plantas cadastradas
""";

        System.out.println(menu);


        // Painel principal
        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10)); // (linhas dinâmicas, 2 colunas, espaço h, espaço v)

        // Criamos uma variável para guardar a escolha
        final int[] escolha = {-1};

        // Mapeamento de opções (Texto do botão -> ID da opção)
        Object[][] opcoes = {
                {"1 - Login", 1},
                {"2 - Novo Usuário", 2},
                {"3 - Info Usuário", 3},
                {"4 - Comprar na Loja", 4},
                {"5 - Concluir Missão", 5},
                {"6 - Cadastrar Missão", 6},
                {"7 - Ver Missões", 7},
                {"8 - Cadastrar Planta", 8},
                {"9 - Ver Plantas", 9}
        };

        // Adiciona um botão para cada opção
        for (Object[] op : opcoes) {
            String texto = (String) op[0];
            int id = (int) op[1];

            JButton btn = new JButton(texto);
            btn.setFocusable(false);
            btn.addActionListener(e -> {
                escolha[0] = id;
                // Fecha a janela ao clicar no botão
                Component comp = (Component) e.getSource();
                JOptionPane.getRootFrame().dispose();
            });
            painel.add(btn);
        }

        // Cabeçalho estilizado com HTML
        String tituloHtml = "<html><center><h2 style='color: #2e7d32; margin:0;'>A R C  G A R D E N</h2>" +
                "<hr><p>O que deseja fazer?</p></center></html>";

        JPanel container = new JPanel(new java.awt.BorderLayout(0, 15));
        container.add(new JLabel(tituloHtml, SwingConstants.CENTER), java.awt.BorderLayout.NORTH);
        container.add(painel, java.awt.BorderLayout.CENTER);

        // Exibe a janela personalizada
        JOptionPane.showOptionDialog(
                null,
                container,
                "Arc Garden - Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Sair"}, // Botão de rodapé
                null
        );
    }
}

*/