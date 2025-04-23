package projeto.dao;

import projeto.model.Palestrante;
import projeto.utilidades.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalestranteDAO {

    public void inserir(Palestrante palestrante) throws SQLException {
        String sql = "INSERT INTO palestrantes (nome, curriculo, area_atuacao) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getCurriculo());
            stmt.setString(3, palestrante.getAreaAtuacao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Palestrante palestrante) throws SQLException {
        String sql = "UPDATE palestrantes SET nome = ?, curriculo = ?, area_atuacao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, palestrante.getNome());
            stmt.setString(2, palestrante.getCurriculo());
            stmt.setString(3, palestrante.getAreaAtuacao());
            stmt.setInt(4, palestrante.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM palestrantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Palestrante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM palestrantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Palestrante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("curriculo"),
                    rs.getString("area_atuacao")
                );
            }
        }
        return null;
    }

    public List<Palestrante> listarTodos() throws SQLException {
        String sql = "SELECT * FROM palestrantes";
        List<Palestrante> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Palestrante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("curriculo"),
                    rs.getString("area_atuacao")
                ));
            }
        }
        return lista;
    }
}
