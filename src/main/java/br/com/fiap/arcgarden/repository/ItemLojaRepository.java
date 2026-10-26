package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.ItemLoja;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemLojaRepository
{
    // --------------- DATA SOURCE ---------------
    private final DataSource dataSource;
    public ItemLojaRepository(DataSource dataSource) { this.dataSource = dataSource; }


    // --------------- CÓDIGOS SQL ---------------
    private static final String SQL_INSERT = "INSERT INTO tb_itens_loja (nome, tipo, preco_agua, loja_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT item_loja_id, nome, tipo, preco_agua, loja_id FROM tb_itens_loja ORDER BY item_loja_id";
    private static final String SQL_SELECT_ID = "SELECT item_loja_id, nome, tipo, preco_agua, loja_id FROM tb_itens_loja WHERE item_loja_id = ?";
    private static final String SQL_SELECT_TIPO = "SELECT item_loja_id, nome, tipo, preco_agua, loja_id FROM tb_itens_loja WHERE lower(tipo) LIKE ? ORDER BY tipo";
    private static final String SQL_UPDATE = "UPDATE tb_itens_loja SET nome = ?, tipo = ?, preco_agua = ?, loja_id = ? WHERE item_loja_id = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_itens_loja WHERE item_loja_id = ?";


    // --------------- CREATE ---------------
    public void create(ItemLoja item) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"item_loja_id"})) {
            pstmt.setString(1, item.getNome());
            pstmt.setString(2, item.getTipo());
            pstmt.setDouble(3, item.getPrecoAgua());
            pstmt.setInt(4, item.getLojaId());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item da loja", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- READ ---------------
    public List<ItemLoja> readAll() {
        List<ItemLoja> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens da loja", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public ItemLoja read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearItem(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item da loja por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    public List<ItemLoja> readByTipo(String tipo) {
        List<ItemLoja> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_TIPO)) {
            pstmt.setString(1, "%" + tipo.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar itens por tipo", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    // --------------- UPDATE ---------------
    public void update(ItemLoja item) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, item.getNome());
            pstmt.setString(2, item.getTipo());
            pstmt.setDouble(3, item.getPrecoAgua());
            pstmt.setInt(4, item.getLojaId());
            pstmt.setInt(5, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item da loja", e);
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
            throw new RuntimeException("Erro ao excluir item da loja", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    private ItemLoja mapearItem(ResultSet rs) throws SQLException {
        return new ItemLoja(
                rs.getInt("item_loja_id"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getDouble("preco_agua"),
                rs.getInt("loja_id")
        );
    }
}