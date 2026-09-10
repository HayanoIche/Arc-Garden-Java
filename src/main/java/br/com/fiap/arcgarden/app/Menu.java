package br.com.fiap.arcgarden.app;

import javax.swing.*;

public class Menu
{
    public static void desenharMenuInicial()
    {
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
        JOptionPane.showInputDialog(menu);
    }
}
