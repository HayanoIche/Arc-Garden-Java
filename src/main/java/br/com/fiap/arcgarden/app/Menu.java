package br.com.fiap.arcgarden.app;

import br.com.fiap.arcgarden.model.Usuario;

import java.awt.*;
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