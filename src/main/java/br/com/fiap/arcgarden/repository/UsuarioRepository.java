package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository
{
    private static String SQL_INSERT = "INSERT INTO tb_usuarios(nome, cpf, arc_score, status) VALUES (?, ?, ?, ?)";

    public int create(Usuario user) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"usuario_id"})) {

            pstmt.setString(1, user.getNome());
            pstmt.setString(2, user.getCpf());
            pstmt.setInt(3, user.getArcScore());
            pstmt.setString(4, user.getStatus());

            int registros = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

            System.out.println("Usuário cadastrado no banco!");
            return registros;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
