package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Missao;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MissaoRepository
{
    // --------------- DATA SOURCE ---------------
    private final DataSource dataSource;
    public MissaoRepository(DataSource dataSource) { this.dataSource = dataSource; }


    // --------------- CÓDIGOS SQL ---------------
    private static final String SQL_INSERT = "INSERT INTO tb_missoes(nome, descricao, dificuldade, vezes, recompensa_pontos) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos FROM tb_missoes ORDER BY missao_id";
    private static final String SQL_SELECT_ID = "SELECT missao_id, nome, descricao, dificuldade, vezes, recompensa_pontos FROM tb_missoes WHERE missao_id = ?";
    private static final String SQL_UPDATE = "UPDATE tb_missoes SET nome = ?, descricao = ?, dificuldade = ?, vezes = ?, recompensa_pontos = ? WHERE missao_id = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_missoes WHERE missao_id = ?";


    // --------------- CREATE ---------------
    public void create(Missao missao) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"missao_id"})) {
            pstmt.setString(1, missao.getNome());
            pstmt.setString(2, missao.getDescricao());
            pstmt.setString(3, missao.getDificuldade());
            pstmt.setInt(4, missao.getVezes());
            pstmt.setInt(5, missao.getRecompensaPontos());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    missao.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir missão", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- READ ---------------
    public List<Missao> readAll() {
        List<Missao> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar missões", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public Missao read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar missão por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    // --------------- UPDATE ---------------
    public void update(Missao missao) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, missao.getNome());
            pstmt.setString(2, missao.getDescricao());
            pstmt.setString(3, missao.getDificuldade());
            pstmt.setInt(4, missao.getVezes());
            pstmt.setInt(5, missao.getRecompensaPontos());
            pstmt.setInt(6, missao.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar missão", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- DELETE ---------------
    public void delete(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir missão", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    private Missao mapear(ResultSet rs) throws SQLException {
        return new Missao(
                rs.getInt("missao_id"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getString("dificuldade"),
                rs.getInt("vezes"),
                rs.getInt("recompensa_pontos")
        );
    }
}