package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Missao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MissaoRepository
{
    // ------------------------------ CÓDIGOS SQL ---------------------------------
    private static final String SQL_INSERT =
            "INSERT INTO tb_missoes(nome, descricao, dificuldade, vezes, recompensa_pontos) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos FROM tb_missoes";

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

    // ------------------------------ READ ---------------------------------
    public List<Missao> readAll() throws Exception
    {
        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL)) {

            Missao missao = null;
            List<Missao> resposta = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                missao = new Missao();

                // missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos
                missao.setId(rs.getInt("missao_id"));
                missao.setNome(rs.getString("nome"));
                missao.setDescricao(rs.getString("descricao"));
                missao.setDificuldade(rs.getString("dificuldade"));
                missao.setVezes(rs.getInt("vezes"));
                missao.setRecompensaPontos(rs.getInt("recompensa_pontos"));

                resposta.add(missao);
            }
            return resposta;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
