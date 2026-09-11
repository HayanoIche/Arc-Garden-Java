package br.com.fiap.arcgarden.repository;

import br.com.fiap.arcgarden.model.ItemLoja;
import br.com.fiap.arcgarden.model.Missao;
import br.com.fiap.arcgarden.model.Planta;
import br.com.fiap.arcgarden.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*

CREATE TABLE tb_tipo_plantas (
     tipo_id INTEGER GENERATED ALWAYS AS IDENTITY,
     nome VARCHAR2(120) NOT NULL,
     categoria VARCHAR2(20) NOT NULL,
     xp_maximo INTEGER NOT NULL,
     descricao VARCHAR2(500) NOT NULL,

     CONSTRAINT tb_tipo_plantas_pk PRIMARY KEY (tipo_id),

     CONSTRAINT tb_tipo_plantas_ck
         CHECK (
             categoria IN ('AGUA', 'ENERGIA', 'RENOVACAO', 'NATUREZA')
                 AND xp_maximo > 0
             )
);

 */

public class PlantaRepository
{
    // ------------------------------ CÓDIGOS SQL ---------------------------------
    private static final String SQL_INSERT =
            "INSERT INTO tb_tipo_plantas(nome, categoria, xp_maximo, descricao) VALUES (?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT tipo_id, nome, categoria, xp_maximo, descricao FROM tb_tipo_plantas";
    // ------------------------------ CREATE ---------------------------------
    public int create(Planta planta) throws Exception
    {
        try (Connection con = ConnectionFactory.getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT, new String[]{"tipo_id"})) {

            pstmt.setString(1, planta.getNome());
            pstmt.setString(2, planta.getCategoria());
            pstmt.setInt(3, planta.getXpMaximo());
            pstmt.setString(4, planta.getDescricao());

            int registros = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    planta.setId(rs.getInt(1));
                }
            }

            System.out.println("Planta cadastrada com sucesso!");
            return registros;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ------------------------------ READ ---------------------------------
    public List<Planta> readAll() throws Exception
    {
        try (Connection con = new ConnectionFactory().getConnection();
            PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_ALL)) {

            Planta planta = null;
            List<Planta> resposta = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                planta = new Planta();

                planta.setId(rs.getInt("tipo_id"));
                planta.setNome(rs.getString("nome"));
                planta.setCategoria(rs.getString("categoria"));
                planta.setXpMaximo(rs.getInt("xp_maximo"));
                planta.setDescricao(rs.getString("descricao"));

                resposta.add(planta);
            }
            return resposta;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
