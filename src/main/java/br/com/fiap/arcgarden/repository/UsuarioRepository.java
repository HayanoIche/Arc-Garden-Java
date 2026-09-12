package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository
{
    // Códigos SQL
    private static final String SQL_INSERT_USUARIO = "INSERT INTO tb_usuarios(nome, cpf, arc_score, status) VALUES (?, ?, ?, ?)";
    private static final String SQL_INSERT_COMPRA = "INSERT INTO tb_itens_comprados(data_compra, quantidade, usuario_id, item_loja_id) VALUES (SYSDATE, ?, ?, ?)";
    private static final String SQL_INSERT_MISSAO_CONCLUIDA = "INSERT INTO tb_missoes_concluidas(missao_id, usuario_id, data_conclusao, pontos_ganhos) VALUES (?, ?, SYSDATE, ?)";

    private static final String SQL_DELETE_USUARIO = "DELETE FROM tb_usuarios WHERE usuario_id = ?";
    private static final String SQL_DELETE_COMPRAS = "DELETE FROM tb_itens_comprados WHERE usuario_id = ?";
    private static final String SQL_DELETE_MISSOES_CONCLUIDAS = "DELETE FROM tb_missoes_concluidas WHERE usuario_id = ?";

    private static final String SQL_SELECT_ID = "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE usuario_id = ?";
    private static final String SQL_SELECT_NOME = "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE lower(nome) LIKE ? ORDER BY nome";
    private static final String SQL_SELECT_CPF = "SELECT usuario_id, nome, cpf, arc_score, status FROM tb_usuarios WHERE lower(cpf) LIKE ? ORDER BY cpf";

    private static final String SQL_SELECT_ITENS =
                    "SELECT l.item_loja_id, l.nome, l.tipo, l.preco_agua " +
                    "FROM tb_itens_comprados c " +
                    "INNER JOIN tb_itens_loja l ON c.item_loja_id = l.item_loja_id " +
                    "WHERE c.usuario_id = ?";
    private static final String SQL_SELECT_MISSOES =
                    "SELECT m.missao_id, m.nome, m.descricao, m.dificuldade, m.vezes, m.recompensa_pontos, mc.data_conclusao " +
                    "FROM tb_missoes_concluidas mc " +
                    "INNER JOIN tb_missoes m ON mc.missao_id = m.missao_id " +
                    "WHERE mc.usuario_id = ?";

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

            if (user.getMissoesConcluidas() != null) {
                for (Missao missao : user.getMissoesConcluidas()) {
                    concluirMissao(con, user.getId(), missao.getId(), missao.getRecompensaPontos());
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

    // REGISTRAR MISSÃO CONCLUÍDA
    public void concluirMissao(int usuarioId, int missaoId, int pontosGanhos) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection()) {
            concluirMissao(con, usuarioId, missaoId, pontosGanhos);
        }
    }

    private void concluirMissao(Connection con, int usuarioId, int missaoId, int pontosGanhos) throws SQLException
    {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_MISSAO_CONCLUIDA)) {
            pstmt.setInt(1, missaoId);
            pstmt.setInt(2, usuarioId);
            pstmt.setInt(3, pontosGanhos);
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
                    user.setMissoesConcluidas(carregarMissoes(con, user.getId()));
                }
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // READ BY NAME
    public List<Usuario> readByName(String name) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NOME)) {

            List<Usuario> lista = new ArrayList<>();
            pstmt.setString(1, "%" + name.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                    user.setMissoesConcluidas(carregarMissoes(con, user.getId()));
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
                    user.setMissoesConcluidas(carregarMissoes(con, user.getId()));
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

            try (PreparedStatement pstmt = con.prepareStatement(SQL_DELETE_MISSOES_CONCLUIDAS)) {
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
    private ArrayList<ItemLoja> carregarItens(Connection con, int usuarioId) throws SQLException
    {
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

    // Auxiliar: Busca as missões concluídas pelo usuário na associativa
    private ArrayList<Missao> carregarMissoes(Connection con, int usuarioId) throws SQLException
    {
        ArrayList<Missao> missoes = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_MISSOES)) {
            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Missao m = new Missao();
                    m.setId(rs.getInt("missao_id"));
                    m.setNome(rs.getString("nome"));
                    m.setDescricao(rs.getString("descricao"));
                    m.setDificuldade(rs.getString("dificuldade"));
                    m.setVezes(rs.getInt("vezes"));
                    m.setRecompensaPontos(rs.getInt("recompensa_pontos"));

                    Date data = rs.getDate("data_conclusao");
                    if (data != null) {
                        m.setDataDeConclusao(data.toLocalDate());
                    }

                    missoes.add(m);
                }
            }
        }
        return missoes;
    }

    // Auxiliar: Instancia e popula a entidade Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException
    {
        Usuario user = new Usuario();
        user.setId(rs.getInt("usuario_id"));
        user.setNome(rs.getString("nome"));
        user.setCpf(rs.getString("cpf"));
        user.setArcScore(rs.getInt("arc_score"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}