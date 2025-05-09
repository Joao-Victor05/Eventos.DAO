package projeto.dao;

import projeto.model.Evento;
import projeto.model.Participante;
import projeto.utilidades.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace();
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

    // Novo método: associar participante a um evento
    public void associarEvento(int participanteId, int eventoId) throws SQLException {
        String sql = "INSERT INTO participantes_eventos (participante_id, evento_id) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();
        }
    }

    // Novo método: listar eventos de um participante
    public List<Evento> listarEventosDoParticipante(int participanteId) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, p.nome AS participante_nome " +
                     "FROM eventos e " +
                     "JOIN participantes_eventos pe ON e.id = pe.evento_id " +
                     "JOIN participantes p ON p.id = pe.participante_id " +
                     "WHERE pe.participante_id = ?";
    
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
    
                    // Se o Evento tiver uma referência ao Participante
                    Participante participante = new Participante();
                    participante.setId(participanteId);
                    participante.setNome(rs.getString("participante_nome"));
                    
                    evento.setNomeParticipante(rs.getString("participante_nome")); // Supondo que exista esse campo
    
                    eventos.add(evento);
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace(); // ou log apropriado
        }
    
        return eventos;
    }
}