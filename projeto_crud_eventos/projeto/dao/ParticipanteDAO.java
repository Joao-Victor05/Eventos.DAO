package projeto.dao;

import java.sql.*;
import java.util.*;
import projeto.model.Participante;
import projeto.model.Evento;
import projeto.utilidades.Conexao;

public class ParticipanteDAO {
    public List<Participante> listar() {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT * FROM participantes";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Participante p = new Participante();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                p.setTelefone(rs.getString("telefone"));
                participantes.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar participantes: " + e.getMessage());
        }
        return participantes;
    }

    public void inserir(Participante participante) throws SQLException {
        String sql = "INSERT INTO participantes (nome, email, telefone) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setString(3, participante.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM participantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Participante participante) throws SQLException {
        String sql = "UPDATE participantes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setString(3, participante.getTelefone());
            stmt.setInt(4, participante.getId());
            stmt.executeUpdate();
        }
    }

    public Participante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM participantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Participante p = new Participante();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setEmail(rs.getString("email"));
                    p.setTelefone(rs.getString("telefone"));
                    return p;
                }
            }
        }
        return null;
    }

    public void associarEvento(int participanteId, int eventoId) throws SQLException {
        String sql = "INSERT INTO participantes_eventos (participante_id, evento_id) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();
        }
    }

    public List<Evento> listarEventosDoParticipante(int participanteId) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.* FROM eventos e JOIN participantes_eventos pe ON e.id = pe.evento_id WHERE pe.participante_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participanteId);
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
            System.err.println("Erro ao listar eventos do participante: " + e.getMessage());
        }
        return eventos;
    }
}