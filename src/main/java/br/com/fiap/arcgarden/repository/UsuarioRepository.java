package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.ItemComprado;
import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.model.MissaoConcluida;
import br.com.fiap.arcgarden.model.Usuario;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository
{
    // --------------- DATA SOURCE ---------------
    private final DataSource dataSource;
    public UsuarioRepository(DataSource dataSource) { this.dataSource = dataSource; }


    // --------------- CÓDIGOS SQL ---------------
    private static final String SQL_INSERT_USUARIO = "INSERT INTO tb_usuarios(nome, cpf, arc_score, status, email, senha) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_COMPRA = "INSERT INTO tb_itens_comprados(data_compra, quantidade, usuario_id, item_loja_id) VALUES (SYSDATE, ?, ?, ?)";
    private static final String SQL_INSERT_MISSAO_CONCLUIDA = "INSERT INTO tb_missoes_concluidas(missao_id, usuario_id, data_conclusao) VALUES (?, ?, SYSDATE)";

    private static final String SQL_SELECT_ALL = "SELECT usuario_id, nome, cpf, arc_score, status, email, senha FROM tb_usuarios ORDER BY usuario_id";
    private static final String SQL_SELECT_ID = "SELECT usuario_id, nome, cpf, arc_score, status, email, senha FROM tb_usuarios WHERE usuario_id = ?";
    private static final String SQL_SELECT_NOME = "SELECT usuario_id, nome, cpf, arc_score, status, email, senha FROM tb_usuarios WHERE lower(nome) LIKE ? ORDER BY nome";
    private static final String SQL_SELECT_CPF = "SELECT usuario_id, nome, cpf, arc_score, status, email, senha FROM tb_usuarios WHERE lower(cpf) LIKE ? ORDER BY cpf";

    private static final String SQL_SELECT_ITENS =
            "SELECT c.item_comprado_id, c.data_compra, c.quantidade, " +
                    "l.item_loja_id, l.nome, l.tipo, l.preco_agua, l.loja_id " +
                    "FROM tb_itens_comprados c " +
                    "INNER JOIN tb_itens_loja l ON c.item_loja_id = l.item_loja_id " +
                    "WHERE c.usuario_id = ?";

    private static final String SQL_SELECT_MISSOES_CONCLUIDAS =
            "SELECT mc.missao_concluida_id, mc.data_conclusao, " +
                    "m.missao_id, m.nome, m.descricao, m.dificuldade, m.vezes, m.recompensa_pontos " +
                    "FROM tb_missoes_concluidas mc " +
                    "INNER JOIN tb_missoes m ON mc.missao_id = m.missao_id " +
                    "WHERE mc.usuario_id = ?";

    private static final String SQL_UPDATE_USUARIO = "UPDATE tb_usuarios SET nome = ?, cpf = ?, arc_score = ?, status = ?, email = ?, senha = ? WHERE usuario_id = ?";


    // --------------- CREATE ---------------
    public void create(Usuario user) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_USUARIO, new String[]{"usuario_id"})) {
            pstmt.setString(1, user.getNome());
            pstmt.setString(2, user.getCpf());
            pstmt.setInt(3, user.getArcScore());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getSenha());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

            if (user.getItensComprados() != null) {
                for (ItemComprado compra : user.getItensComprados()) {
                    comprarItem(con, user.getId(), compra.getItemLoja().getId(), compra.getQuantidade());
                }
            }

            if (user.getMissoesConcluidas() != null) {
                for (MissaoConcluida mc : user.getMissoesConcluidas()) {
                    concluirMissao(con, user.getId(), mc.getMissao().getId());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    public void inserirMissaoConcluida(int usuarioId, int missaoId) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_MISSAO_CONCLUIDA)) {
            pstmt.setLong(1, missaoId);
            pstmt.setLong(2, usuarioId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar missão concluída: " + e.getMessage(), e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    public void inserirItemComprado(int usuarioId, int itemId, int quantidade) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_COMPRA)) {
            pstmt.setInt(1, quantidade);
            pstmt.setLong(2, usuarioId);
            pstmt.setLong(3, itemId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar compra de item: " + e.getMessage(), e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- READ ---------------
    public List<Usuario> readAll()
    {
        List<Usuario> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario user = mapearUsuario(rs);
                user.setItensComprados(carregarItens(con, user.getId()));
                user.setMissoesConcluidas(carregarMissoesConcluidas(con, user.getId()));
                lista.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public Usuario read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                    user.setMissoesConcluidas(carregarMissoesConcluidas(con, user.getId()));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    public List<Usuario> readByName(String name) {
        List<Usuario> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_NOME)) {
            pstmt.setString(1, "%" + name.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Usuario user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                    user.setMissoesConcluidas(carregarMissoesConcluidas(con, user.getId()));
                    lista.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários por nome", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public Usuario readByCpf(String cpf) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_CPF)) {
            pstmt.setString(1, "%" + cpf.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario user = mapearUsuario(rs);
                    user.setItensComprados(carregarItens(con, user.getId()));
                    user.setMissoesConcluidas(carregarMissoesConcluidas(con, user.getId()));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por CPF", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    // --------------- UPDATE ---------------
    public void update(Usuario user) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE_USUARIO)) {
            pstmt.setString(1, user.getNome());
            pstmt.setString(2, user.getCpf());
            pstmt.setInt(3, user.getArcScore());
            pstmt.setString(4, user.getStatus());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getSenha());
            pstmt.setLong(7, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    private void comprarItem(Connection con, int usuarioId, int itemLojaId, int quantidade) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_COMPRA)) {
            pstmt.setInt(1, quantidade);
            pstmt.setInt(2, usuarioId);
            pstmt.setInt(3, itemLojaId);
            pstmt.executeUpdate();
        }
    }


    private void concluirMissao(Connection con, int usuarioId, int missaoId) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_MISSAO_CONCLUIDA)) {
            pstmt.setInt(1, missaoId);
            pstmt.setInt(2, usuarioId);
            pstmt.executeUpdate();
        }
    }


    private ArrayList<ItemComprado> carregarItens(Connection con, int usuarioId) throws SQLException {
        ArrayList<ItemComprado> itensComprados = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ITENS)) {
            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ItemLoja loja = new ItemLoja(
                            rs.getInt("item_loja_id"),
                            rs.getString("nome"),
                            rs.getString("tipo"),
                            rs.getDouble("preco_agua"),
                            rs.getInt("loja_id")
                    );

                    ItemComprado compra = new ItemComprado(
                            rs.getInt("item_comprado_id"),
                            rs.getDate("data_compra") != null ? rs.getDate("data_compra").toLocalDate() : null,
                            rs.getInt("quantidade"),
                            usuarioId,
                            loja
                    );

                    itensComprados.add(compra);
                }
            }
        }
        return itensComprados;
    }


    private ArrayList<MissaoConcluida> carregarMissoesConcluidas(Connection con, int usuarioId) throws SQLException {
        ArrayList<MissaoConcluida> missoesConcluidas = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_MISSOES_CONCLUIDAS)) {
            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Missao missao = new Missao(
                            rs.getInt("missao_id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("dificuldade"),
                            rs.getInt("vezes"),
                            rs.getInt("recompensa_pontos")
                    );

                    MissaoConcluida mc = new MissaoConcluida(
                            rs.getInt("missao_concluida_id"),
                            usuarioId,
                            missao,
                            rs.getDate("data_conclusao") != null ? rs.getDate("data_conclusao").toLocalDate() : null,
                            missao.getRecompensaPontos()
                    );

                    missoesConcluidas.add(mc);
                }
            }
        }
        return missoesConcluidas;
    }


    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario user = new Usuario();
        user.setId(rs.getInt("usuario_id"));
        user.setNome(rs.getString("nome"));
        user.setCpf(rs.getString("cpf"));
        user.setArcScore(rs.getInt("arc_score"));
        user.setStatus(rs.getString("status"));
        user.setEmail(rs.getString("email"));
        user.setSenha(rs.getString("senha"));
        return user;
    }
}
