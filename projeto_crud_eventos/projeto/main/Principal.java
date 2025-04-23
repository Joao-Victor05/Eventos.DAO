package projeto.main;

import projeto.model.Evento;
import projeto.model.Participante;
import projeto.dao.EventoDAO;
import projeto.dao.ParticipanteDAO;
import projeto.utilidades.Conexao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventoDAO eventoDAO = new EventoDAO();
    private static final ParticipanteDAO participanteDAO = new ParticipanteDAO();

    public static void main(String[] args) {
        try {
            Conexao.createTable();
            Conexao.createTableParticipantes();
    
            System.out.println("Bem-vindo ao sistema de eventos!\n");
    
            int opcao;
            do {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Cadastrar evento");
                System.out.println("2 - Listar eventos");
                System.out.println("3 - Cadastrar participante");
                System.out.println("4 - Listar participantes");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
    
                switch (opcao) {
                    case 1 -> cadastrarEvento();
                    case 2 -> listarEventos();
                    case 3 -> cadastrarParticipante();
                    case 4 -> listarParticipantes();
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }
            } while (opcao != 0);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void cadastrarEvento() throws SQLException {
        System.out.println("\n--- Cadastro de Evento ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());
        System.out.print("Local: ");
        String local = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Evento evento = new Evento(nome, data, local, descricao);
        eventoDAO.inserir(evento);

        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listarEventos() throws SQLException {
        List<Evento> eventos = eventoDAO.listarTodos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            System.out.println("\n--- Eventos cadastrados ---");
            for (Evento e : eventos) {
                System.out.println(e);
            }
        }
    }

    private static void cadastrarParticipante() throws SQLException {
        System.out.println("\n--- Cadastro de Participante ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Participante participante = new Participante(0, nome, email, telefone);
        participanteDAO.inserir(participante);

        System.out.println("Participante cadastrado com sucesso!");
    }

    private static void listarParticipantes() throws SQLException {
        List<Participante> participantes = participanteDAO.listarTodos();
        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
        } else {
            System.out.println("\n--- Participantes cadastrados ---");
            for (Participante p : participantes) {
                System.out.println(p);
            }
        }
    }
}
