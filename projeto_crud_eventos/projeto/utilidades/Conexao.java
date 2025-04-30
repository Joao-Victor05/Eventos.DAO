package projeto.utilidades;

import java.sql.Connection;
import java.sql.DriverManager; 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
    public static void createDatabase() {
    String url = "jdbc:mysql://localhost:3306/";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            // Cria o banco de dados se não existir
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS eventos");
            System.out.println("Banco de dados 'eventos' criado ou já existe.");
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar banco de dados: " + e.getMessage());
        }
    } catch (ClassNotFoundException e) {
        System.out.println("Driver MySQL não encontrado: " + e.getMessage());
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
    public static void createTableParticipantesEventos() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS participantes_eventos (" +
                     "participante_id INT, " +
                     "evento_id INT, " +
                     "PRIMARY KEY (participante_id, evento_id), " +
                     "FOREIGN KEY (participante_id) REFERENCES participantes(id), " +
                     "FOREIGN KEY (evento_id) REFERENCES eventos(id))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.executeUpdate();
        }
    }
    public static void createTablePalestrantes() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS palestrantes (" +
                     "id INT PRIMARY KEY AUTO_INCREMENT, " +
                     "nome VARCHAR(100) NOT NULL, " +
                     "curriculo TEXT, " +
                     "area_atuacao VARCHAR(100))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("Criando tabela palestrantes...");
            stmt.executeUpdate();
            System.out.println("Tabela palestrantes criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela palestrantes: " + e.getMessage());
            throw e;
        }
    }
    public static void createTablePalestrantesEventos() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS palestrantes_eventos (" +
                     "palestrante_id INT, " +
                     "evento_id INT, " +
                     "PRIMARY KEY (palestrante_id, evento_id), " +
                     "FOREIGN KEY (palestrante_id) REFERENCES palestrantes(id), " +
                     "FOREIGN KEY (evento_id) REFERENCES eventos(id))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}
