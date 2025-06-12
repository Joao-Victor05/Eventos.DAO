package projeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import projeto.model.Evento;
import projeto.utilidades.Conexao;

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
            try (ResultSet rs = stmt.executeQuery()) {
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
        }
        return null;
    }

    public List<Evento> listarTodos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos";
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