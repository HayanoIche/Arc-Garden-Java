package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Missao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MissaoRepository
{
    // ------------------------------ CÓDIGOS SQL ---------------------------------
    private static final String SQL_INSERT =
            "INSERT INTO tb_missoes(nome, descricao, dificuldade, vezes, recompensa_pontos) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_USUARIO =
            "DELETE FROM tb_missoes WHERE missao_id = ?";

    private static final String SQL_SELECT_ID =
            "SELECT missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos FROM tb_missoes WHERE missao_id = ?";
    private static final String SQL_SELECT_NOME =
            "SELECT missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos FROM tb_missoes WHERE lower(nome) LIKE ? ORDER BY nome";

    // ------------------------------ CREATE ---------------------------------
    public int create(Missao missao) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"missao_id"})) {

            pstmt.setString(1, missao.getNome());
            pstmt.setString(2, missao.getDescricao());
            pstmt.setString(3, missao.getDificuldade());
            pstmt.setInt(4, missao.getVezes());
            pstmt.setInt(5, missao.getRecompensaPontos());

            int registros = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys())
            {
                if (rs.next()) {
                    missao.setId(rs.getInt(1));
                }
            }

            System.out.println("Missão cadastrada com sucesso!");
            return registros;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
