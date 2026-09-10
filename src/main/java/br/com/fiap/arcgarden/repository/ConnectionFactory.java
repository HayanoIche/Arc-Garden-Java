package br.com.fiap.arcgarden.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory
{
    public static void main(String[] args)
    {
        System.out.println("Teste de conexão...");

        try (Connection con = ConnectionFactory.getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println("Conexão estabelecida com sucesso!");
                System.out.println("Banco: " + con.getMetaData().getDatabaseProductName());
                System.out.println("Versão: " + con.getMetaData().getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            System.err.println("Falha ao fechar ou validar a conexão.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados:");
            System.err.println(e.getMessage());
        }
    }

    // Atributos Hardcoded
    private static String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";
    private static String USER = "RM571751";
    private static String PASS = "280508";

    public static Connection getConnection()
    {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
