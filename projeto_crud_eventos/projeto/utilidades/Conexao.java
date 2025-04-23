package projeto.utilidades;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/eventos";
    private static final String USER = "root";
    private static final String PASSWORD = "root@123";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado", e);
        }
    }

public static void createTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS eventos (" +
                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
                 "nome VARCHAR(100) NOT NULL, " +
                 "data DATE NOT NULL, " +
                 "local VARCHAR(100) NOT NULL, " +
                 "descricao VARCHAR(100) NOT NULL)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Tabela 'eventos' criada ou já existe.");
        }
    }
    public static void createTableParticipantes() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS participantes (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "nome VARCHAR(100) NOT NULL, " +
                     "email VARCHAR(100) NOT NULL, " +
                     "telefone VARCHAR(20) NOT NULL)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Tabela 'participantes' criada ou já existe.");
        }
    }
}
