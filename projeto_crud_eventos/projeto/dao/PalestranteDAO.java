package projeto.dao;

import java.sql.*;
import java.util.*;
import projeto.model.Palestrante;
import projeto.model.Evento;
import projeto.utilidades.Conexao;

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
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Palestrante(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("curriculo"),
                        rs.getString("area_atuacao")
                    );
                }
            }
        }
        return null;
    }

    public List<Palestrante> listarTodos() throws SQLException {
        List<Palestrante> lista = new ArrayList<>();
        String sql = "SELECT * FROM palestrantes";
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

    public void associarEvento(int palestranteId, int eventoId) throws SQLException {
        String sql = "INSERT INTO palestrantes_eventos (palestrante_id, evento_id) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, palestranteId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();
        }
    }

    public List<Evento> listarEventosDoPalestrante(int palestranteId) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.* FROM eventos e JOIN palestrantes_eventos pe ON e.id = pe.evento_id WHERE pe.palestrante_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, palestranteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Evento evento = new Evento();
                    evento.setId(rs.getInt("id"));
                    evento.setNome(rs.getString("nome"));
                    evento.setData(rs.getDate("data").toLocalDate());
                    evento.setLocal(rs.getString("local"));
                    evento.setDescricao(rs.getString("descricao"));
                    eventos.add(evento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos do palestrante: " + e.getMessage());
        }
        return eventos;
    }
}