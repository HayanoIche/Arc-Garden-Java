package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.TipoPlanta;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TipoPlantaRepository
{
    private final DataSource dataSource;
    public TipoPlantaRepository(DataSource dataSource) { this.dataSource = dataSource; }

    private static final String SQL_INSERT = "INSERT INTO tb_tipo_plantas(nome, categoria, xp_maximo, descricao) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT tipo_id, nome, categoria, xp_maximo, descricao FROM tb_tipo_plantas ORDER BY tipo_id";
    private static final String SQL_SELECT_ID = "SELECT tipo_id, nome, categoria, xp_maximo, descricao FROM tb_tipo_plantas WHERE tipo_id = ?";
    private static final String SQL_SELECT_CATEGORIA = "SELECT tipo_id, nome, categoria, xp_maximo, descricao FROM tb_tipo_plantas WHERE lower(categoria) LIKE ? ORDER BY nome";
    private static final String SQL_UPDATE = "UPDATE tb_tipo_plantas SET nome = ?, categoria = ?, xp_maximo = ?, descricao = ? WHERE tipo_id = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_tipo_plantas WHERE tipo_id = ?";

    public void create(TipoPlanta tipo) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"tipo_id"})) {
            pstmt.setString(1, tipo.getNome());
            pstmt.setString(2, tipo.getCategoria());
            pstmt.setInt(3, tipo.getXpMaximo());
            pstmt.setString(4, tipo.getDescricao());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tipo.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir tipo de planta", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    public List<TipoPlanta> readAll() {
        List<TipoPlanta> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tipos de plantas", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }

    public TipoPlanta read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipo de planta por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }

    public List<TipoPlanta> readByCategoria(String categoria) {
        List<TipoPlanta> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_CATEGORIA)) {
            pstmt.setString(1, "%" + categoria.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipos de plantas por categoria", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }

    public void update(TipoPlanta tipo) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, tipo.getNome());
            pstmt.setString(2, tipo.getCategoria());
            pstmt.setInt(3, tipo.getXpMaximo());
            pstmt.setString(4, tipo.getDescricao());
            pstmt.setInt(5, tipo.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tipo de planta", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    public void delete(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir tipo de planta", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }

    private TipoPlanta mapear(ResultSet rs) throws SQLException {
        return new TipoPlanta(
                rs.getInt("tipo_id"),
                rs.getString("nome"),
                rs.getString("categoria"),
                rs.getInt("xp_maximo"),
                rs.getString("descricao")
        );
    }
}