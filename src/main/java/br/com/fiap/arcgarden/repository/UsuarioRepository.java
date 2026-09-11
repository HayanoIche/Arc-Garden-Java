package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository
{
    // Códigos SQL
    private static final String SQL_INSERT_USUARIO =
            "INSERT INTO tb_usuarios(nome, cpf, arc_score, status) VALUES (?, ?, ?, ?)";
    private static final String SQL_INSERT_COMPRA =
            "INSERT INTO tb_itens_comprados(data_compra, quantidade, usuario_id, item_loja_id) VALUES (SYSDATE, ?, ?, ?)";
    private static final String SQL_DELETE_USUARIO =
            "DELETE FROM tb_usuarios WHERE usuario_id = ?";
    private static final String SQL_DELETE_COMPRAS =
            "DELETE FROM tb_itens_comprados WHERE usuario_id = ?";
    private static final String SQL_SELECT_ID =
            "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE usuario_id = ?";
    private static final String SQL_SELECT_NOME =
            "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE lower(nome) LIKE ? ORDER BY nome";
    private static final String SQL_SELECT_CPF =
            "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE lower(cpf) LIKE ? ORDER BY cpf";
    private static final String SQL_SELECT_ITENS =
            "SELECT l.item_loja_id, l.nome, l.tipo, l.preco_agua " +
                    "FROM tb_itens_comprados c " +
                    "INNER JOIN tb_itens_loja l ON c.item_loja_id = l.item_loja_id " +
                    "WHERE c.usuario_id = ?";

    // CREATE
    public int create(Usuario user) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_USUARIO, new String[]{"usuario_id"})) {
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

            if (user.getItensComprados() != null) {
                for (ItemLoja item : user.getItensComprados()) {
                    comprarItem(con, user.getId(), item.getId(), 1);
                }
            }

            System.out.println("Usuário cadastrado com sucesso!");
            return registros;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // REGISTRAR COMPRA DE ITEM
    public void registrarCompra(int usuarioId, int itemLojaId, int quantidade) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection()) {
            comprarItem(con, usuarioId, itemLojaId, quantidade);
        }
    }

    private void comprarItem(Connection con, int usuarioId, int itemLojaId, int quantidade) throws SQLException
    {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_COMPRA)) {
            pstmt.setInt(1, quantidade);
            pstmt.setInt(2, usuarioId);
            pstmt.setInt(3, itemLojaId);
            pstmt.executeUpdate();
        }
    }

    // READ BY ID
    public Usuario read(long id) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {

            pstmt.setLong(1, id);
            Usuario user = null;

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                }
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // READ BY NAME
    public List<Usuario> readByName(String name) throws Exception {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NOME)) {

            List<Usuario> lista = new ArrayList<>();
            pstmt.setString(1, "%" + name.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                    lista.add(user);
                }
            }

            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // READ BY CPF
    public Usuario readByCpf(String cpf) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_CPF)) {

            Usuario user = null;
            pstmt.setString(1, "%" + cpf.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                }
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // DELETE
    public int delete(int id) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection()) {
            try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_COMPRAS)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_USUARIO)) {
                pstmt.setInt(1, id);
                return pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Auxiliar: Busca os itens comprados pelo usuário na associativa
    private ArrayList<ItemLoja> carregarItens(Connection con, int usuarioId) throws SQLException {
        ArrayList<ItemLoja> itens = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ITENS)) {
            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ItemLoja item = new ItemLoja();
                    item.setId(rs.getInt("item_loja_id"));
                    item.setNome(rs.getString("nome"));
                    item.setTipo(rs.getString("tipo"));
                    item.setPrecoAgua(rs.getBigDecimal("preco_agua"));
                    itens.add(item);
                }
            }
        }
        return itens;
    }

    // Auxiliar: Instancia e popula a entidade Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setId(rs.getInt("usuario_id"));
        user.setNome(rs.getString("nome"));
        user.setCpf(rs.getString("cpf"));
        user.setArcScore(rs.getInt("arc_score"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}