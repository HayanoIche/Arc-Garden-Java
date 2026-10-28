package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Loja;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LojaRepository
{
    // --------------- DATA SOURCE ---------------
    private final DataSource dataSource;
    public LojaRepository(DataSource dataSource) { this.dataSource = dataSource; }


    // --------------- CÓDIGOS SQL ---------------
    private static final String SQL_INSERT = "INSERT INTO tb_loja(atualizacao) VALUES (?)";
    private static final String SQL_SELECT_ALL = "SELECT loja_id, atualizacao FROM tb_loja ORDER BY loja_id";
    private static final String SQL_SELECT_ID = "SELECT loja_id, atualizacao FROM tb_loja WHERE loja_id = ?";
    private static final String SQL_UPDATE = "UPDATE tb_loja SET atualizacao = ? WHERE loja_id = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_loja WHERE loja_id = ?";


    // --------------- CREATE ---------------
    public void create(Loja loja) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"loja_id"})) {
            pstmt.setDate(1, Date.valueOf(loja.getAtualizacao()));
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    loja.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir loja", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- READ ---------------
    public List<Loja> readAll() {
        List<Loja> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar lojas", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public Loja read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar loja por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    // --------------- UPDATE ---------------
    public void update(Loja loja) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE)) {
            pstmt.setDate(1, Date.valueOf(loja.getAtualizacao()));
            pstmt.setInt(2, loja.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar loja", e);
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
            throw new RuntimeException("Erro ao excluir loja", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    private Loja mapear(ResultSet rs) throws SQLException {
        Date sqlDate = rs.getDate("atualizacao");
        LocalDate localDate = sqlDate != null ? sqlDate.toLocalDate() : null;
        return new Loja(
                rs.getInt("loja_id"),
                localDate
        );
    }
}
