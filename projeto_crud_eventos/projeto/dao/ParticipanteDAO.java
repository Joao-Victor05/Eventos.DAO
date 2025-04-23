package projeto.dao;

import projeto.model.Participante;
import projeto.utilidades.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

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

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM participantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Participante buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM participantes WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Participante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
            }
        }
        return null;
    }

    public List<Participante> listarTodos() throws SQLException {
        String sql = "SELECT * FROM participantes";
        List<Participante> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Participante(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("telefone")
                ));
            }
        }
        return lista;
    }
}
