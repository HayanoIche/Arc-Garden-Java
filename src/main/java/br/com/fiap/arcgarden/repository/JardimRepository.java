package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.Jardim;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.TipoPlanta;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JardimRepository
{
    // --------------- DATA SOURCE ---------------
    private final DataSource dataSource;
    public JardimRepository(DataSource dataSource) { this.dataSource = dataSource; }


    // --------------- CÓDIGOS SQL ---------------
    private static final String SQL_INSERT = "INSERT INTO tb_jardins(usuario_id, nome, tema) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT jardim_id, usuario_id, nome, tema FROM tb_jardins ORDER BY jardim_id";
    private static final String SQL_SELECT_ID = "SELECT jardim_id, usuario_id, nome, tema FROM tb_jardins WHERE jardim_id = ?";
    private static final String SQL_SELECT_USUARIO = "SELECT jardim_id, usuario_id, nome, tema FROM tb_jardins WHERE usuario_id = ?";
    private static final String SQL_SELECT_TEMA = "SELECT jardim_id, usuario_id, nome, tema FROM tb_jardins WHERE lower(tema) LIKE ? ORDER BY tema";

    private static final String SQL_SELECT_PLANTAS =
            "SELECT p.planta_id, p.raridade, p.nivel_planta, p.premiada, p.xp_atual, " +
                    "t.tipo_id, t.nome AS tipo_nome, t.categoria, t.xp_maximo, t.descricao " +
                    "FROM tb_plantas p " +
                    "INNER JOIN tb_tipo_plantas t ON p.tipo_id = t.tipo_id " +
                    "WHERE p.jardim_id = ?";

    private static final String SQL_UPDATE = "UPDATE tb_jardins SET nome = ?, tema = ? WHERE jardim_id = ?";
    private static final String SQL_DELETE = "DELETE FROM tb_jardins WHERE jardim_id = ?";
    private static final String SQL_INSERT_PLANTA =
            "INSERT INTO tb_plantas(jardim_id, tipo_id, raridade, nivel_planta, premiada, xp_atual) VALUES (?, ?, ?, ?, ?, ?)";


    // --------------- CREATE ---------------
    public void create(Jardim jardim) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"jardim_id"})) {
            pstmt.setInt(1, jardim.getUsuarioId());
            pstmt.setString(2, jardim.getNome());
            pstmt.setString(3, jardim.getTema());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    jardim.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir jardim", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    public void inserirPlanta(int jardimId, Planta planta) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_PLANTA, new String[]{"planta_id"})) {
            pstmt.setInt(1, jardimId);
            pstmt.setInt(2, planta.getTipoPlanta().getId());
            pstmt.setString(3, planta.getRaridade());
            pstmt.setString(4, planta.getNivelPlanta());
            pstmt.setString(5, planta.getPremiada());
            pstmt.setInt(6, planta.getXpAtual());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    planta.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir planta no jardim", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    // --------------- READ ---------------
    public List<Jardim> readAll() {
        List<Jardim> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Jardim jardim = mapearJardim(rs);
                jardim.setPlantas(carregarPlantas(con, jardim.getId()));
                lista.add(jardim);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jardins", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    public Jardim read(int id) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ID)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Jardim jardim = mapearJardim(rs);
                    jardim.setPlantas(carregarPlantas(con, jardim.getId()));
                    return jardim;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jardim por ID", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    public Jardim readByUsuarioId(int usuarioId) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_USUARIO)) {
            pstmt.setInt(1, usuarioId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Jardim jardim = mapearJardim(rs);
                    jardim.setPlantas(carregarPlantas(con, jardim.getId()));
                    return jardim;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jardim por ID do usuário", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return null;
    }


    public List<Jardim> readByTema(String tema) {
        List<Jardim> lista = new ArrayList<>();
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_TEMA)) {
            pstmt.setString(1, "%" + tema.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Jardim jardim = mapearJardim(rs);
                    jardim.setPlantas(carregarPlantas(con, jardim.getId()));
                    lista.add(jardim);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jardins por tema", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
        return lista;
    }


    // --------------- UPDATE ---------------
    public void update(Jardim jardim) {
        Connection con = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement pstmt = con.prepareStatement(SQL_UPDATE)) {
            pstmt.setString(1, jardim.getNome());
            pstmt.setString(2, jardim.getTema());
            pstmt.setInt(3, jardim.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar jardim", e);
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
            throw new RuntimeException("Erro ao excluir jardim", e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
        }
    }


    private ArrayList<Planta> carregarPlantas(Connection con, int jardimId) throws SQLException {
        ArrayList<Planta> plantas = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_PLANTAS)) {
            pstmt.setInt(1, jardimId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TipoPlanta tipo = new TipoPlanta(
                            rs.getInt("tipo_id"),
                            rs.getString("tipo_nome"),
                            rs.getString("categoria"),
                            rs.getInt("xp_maximo"),
                            rs.getString("descricao")
                    );

                    Planta planta = new Planta(
                            rs.getInt("planta_id"),
                            jardimId,
                            tipo,
                            rs.getString("raridade"),
                            rs.getString("nivel_planta"),
                            rs.getString("premiada"),
                            rs.getInt("xp_atual")
                    );

                    plantas.add(planta);
                }
            }
        }
        return plantas;
    }


    private Jardim mapearJardim(ResultSet rs) throws SQLException {
        Jardim jardim = new Jardim();
        jardim.setId(rs.getInt("jardim_id"));
        jardim.setUsuarioId(rs.getInt("usuario_id"));
        jardim.setNome(rs.getString("nome"));
        jardim.setTema(rs.getString("tema"));
        return jardim;
    }
}