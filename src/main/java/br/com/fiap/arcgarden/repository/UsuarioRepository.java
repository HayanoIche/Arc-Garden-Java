package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository
{
    private static String SQL_INSERT = "INSERT INTO tb_usuarios(nome, cpf, arc_score, status) VALUES (?, ?, ?, ?)";
    private static String SQL_DELETE = "DELETE FROM tb_usuarios WHERE usuario_id = ?";
    private static String SQL_SELECT_NOME = "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE lower(nome) LIKE ? ORDER BY nome";
    private static String SQL_SELECT_ID = "SELECT id, nome, telefone, nascimento FROM tb_paciente WHERE id = ?";

    // CREATE
    public int create(Usuario user) throws Exception {
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

    // READ
    public List<Usuario> readByName(String name) throws Exception
    {
        try (Connection con = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NOME)) {

            Usuario user = null;
            List<Usuario> resposta = new ArrayList<>();

            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                user = new Usuario();
                user.setId(rs.getInt("usuario_id"));
                user.setNome(rs.getString("nome"));
                user.setCpf(rs.getString("cpf"));
                user.setArcScore(rs.getInt("arc_score"));
                user.setStatus(rs.getString("status"));
                resposta.add(user);
            }

            return resposta;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // READ
    public Usuario read(long id) throws Exception
    {
        try (Connection con = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
                Usuario user = new Usuario();
                pstmt.setLong(1, id);

                ResultSet rs = pstmt.executeQuery();

                while(rs.next())
                {
                    user.setId(rs.getInt("usuario_id"));
                    user.setNome(rs.getString("nome"));
                    user.setCpf(rs.getString("cpf"));
                    user.setStatus(rs.getString("status"));
                    user.setArcScore(rs.getInt("arc_score"));
                }

                return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // DELETE
    public int delete(int id) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_DELETE)) {

            pstmt.setInt(1, id);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
