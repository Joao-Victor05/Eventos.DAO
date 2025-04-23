package projeto.dao;

import projeto.model.Evento;
import projeto.utilidades.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    public void inserir(Evento evento) throws SQLException {
        String sql = "INSERT INTO eventos (nome, data, local, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento.getNome());
            stmt.setDate(2, Date.valueOf(evento.getData()));
            stmt.setString(3, evento.getLocal());
            stmt.setString(4, evento.getDescricao());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Evento evento) throws SQLException {
        String sql = "UPDATE eventos SET nome = ?, data = ?, local = ?, descricao = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento.getNome());
            stmt.setDate(2, Date.valueOf(evento.getData()));
            stmt.setString(3, evento.getLocal());
            stmt.setString(4, evento.getDescricao());
            stmt.setInt(5, evento.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Evento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM eventos WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evento(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("data").toLocalDate(),
                    rs.getString("local"),
                    rs.getString("descricao")
                );
            }
        }
        return null;
    }

    public List<Evento> listarTodos() throws SQLException {
        String sql = "SELECT * FROM eventos";
        List<Evento> eventos = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                eventos.add(new Evento(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("data").toLocalDate(),
                    rs.getString("local"),
                    rs.getString("descricao")
                ));
            }
        }
        return eventos;
    }
}