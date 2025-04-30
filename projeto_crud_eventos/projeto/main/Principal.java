package projeto.main;

import projeto.model.Evento;
import projeto.model.Participante;
import projeto.model.Palestrante;
import projeto.dao.EventoDAO;
import projeto.dao.ParticipanteDAO;
import projeto.dao.PalestranteDAO;
import projeto.utilidades.Conexao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventoDAO eventoDAO = new EventoDAO();
    private static final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private static final PalestranteDAO palestranteDAO = new PalestranteDAO();

    public static void main(String[] args) {
        try {
            Conexao.createTable();
            Conexao.createTableParticipantes();
            Conexao.createTableParticipantesEventos();
            Conexao.createTablePalestrantes();
            Conexao.createTablePalestrantesEventos();
    
            System.out.println("Bem-vindo ao sistema de eventos!\n");
    
            int opcao;
            do {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Cadastrar evento");
                System.out.println("2 - Listar eventos");
                System.out.println("3 - Cadastrar participante");
                System.out.println("4 - Listar participantes");
                System.out.println("5 - Cadastrar palestrante");
                System.out.println("6 - Listar palestrantes");
                System.out.println("7 - Associar participante a evento");
                System.out.println("8 - Associar palestrante a evento");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();
    
                switch (opcao) {
                    case 1 -> cadastrarEvento();
                    case 2 -> listarEventos();
                    case 3 -> cadastrarParticipante();
                    case 4 -> listarParticipantes();
                    case 5 -> cadastrarPalestrante();
                    case 6 -> listarPalestrantes();
                    case 7 -> associarParticipanteEvento();
                    case 8 -> associarPalestranteEvento();
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }
            } while (opcao != 0);
    
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o sistema: " + e.getMessage());
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
        List<Participante> participantes = participanteDAO.listar();
        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
        } else {
            System.out.println("\n--- Participantes cadastrados ---");
            for (Participante p : participantes) {
                System.out.println(p);
                List<Evento> eventos = participanteDAO.listarEventosDoParticipante(p.getId());
                if (!eventos.isEmpty()) {
                    System.out.println("  Eventos associados:");
                    for (Evento e : eventos) {
                        System.out.println("    - " + e.getNome() + " (Participante: " + e.getNomeParticipante() + ")");
                    }
                }
            }
        }
    }

    private static void associarParticipanteEvento() throws SQLException {
        System.out.println("\n--- Associar Participante a Evento ---");

        List<Participante> participantes = participanteDAO.listar();
        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
            return;
        }
        System.out.println("Participantes disponíveis:");
        for (Participante p : participantes) {
            System.out.println(p);
        }
        System.out.print("Digite o ID do participante: ");
        int participanteId = scanner.nextInt();
        scanner.nextLine();

        Participante participante = participantes.stream()
                .filter(p -> p.getId() == participanteId)
                .findFirst()
                .orElse(null);
        if (participante == null) {
            System.out.println("Participante não encontrado.");
            return;
        }

        List<Evento> eventos = eventoDAO.listarTodos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }
        System.out.println("Eventos disponíveis:");
        for (Evento e : eventos) {
            System.out.println(e);
        }
        System.out.print("Digite o ID do evento: ");
        int eventoId = scanner.nextInt();
        scanner.nextLine();

        Evento evento = eventos.stream()
                .filter(e -> e.getId() == eventoId)
                .findFirst()
                .orElse(null);
        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        try {
            participanteDAO.associarEvento(participanteId, eventoId);
            System.out.println("Participante associado ao evento com sucesso!");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Este participante já está associado a este evento.");
            } else {
                System.out.println("Erro ao associar participante ao evento: " + e.getMessage());
            }
        }
    }

    private static void cadastrarPalestrante() throws SQLException {
        System.out.println("\n--- Cadastro de Palestrante ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Currículo: ");
        String curriculo = scanner.nextLine();
        System.out.print("Área de Atuação: ");
        String areaAtuacao = scanner.nextLine();

        Palestrante palestrante = new Palestrante(0, nome, curriculo, areaAtuacao);
        palestranteDAO.inserir(palestrante);

        System.out.println("Palestrante cadastrado com sucesso!");
    }

    private static void listarPalestrantes() throws SQLException {
        List<Palestrante> palestrantes = palestranteDAO.listarTodos();
        if (palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante cadastrado.");
        } else {
            System.out.println("\n--- Palestrantes cadastrados ---");
            for (Palestrante p : palestrantes) {
                System.out.println(p);
                List<Evento> eventos = palestranteDAO.listarEventosDoPalestrante(p.getId());
                if (!eventos.isEmpty()) {
                    System.out.println("  Eventos associados:");
                    for (Evento e : eventos) {
                        System.out.println("    - " + e.getNome() + " (Palestrante: " + e.getPalestrante() + ")");
                    }
                }
            }
        }
    }

    private static void associarPalestranteEvento() throws SQLException {
        System.out.println("\n--- Associar Palestrante a Evento ---");

        List<Palestrante> palestrantes = palestranteDAO.listarTodos();
        if (palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante cadastrado.");
            return;
        }
        System.out.println("Palestrantes disponíveis:");
        for (Palestrante p : palestrantes) {
            System.out.println(p);
        }
        System.out.print("Digite o ID do palestrante: ");
        int palestranteId = scanner.nextInt();
        scanner.nextLine();

        Palestrante palestrante = palestrantes.stream()
                .filter(p -> p.getId() == palestranteId)
                .findFirst()
                .orElse(null);
        if (palestrante == null) {
            System.out.println("Palestrante não encontrado.");
            return;
        }

        List<Evento> eventos = eventoDAO.listarTodos();
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
            return;
        }
        System.out.println("Eventos disponíveis:");
        for (Evento e : eventos) {
            System.out.println(e);
        }
        System.out.print("Digite o ID do evento: ");
        int eventoId = scanner.nextInt();
        scanner.nextLine();

        Evento evento = eventos.stream()
                .filter(e -> e.getId() == eventoId)
                .findFirst()
                .orElse(null);
        if (evento == null) {
            System.out.println("Evento não encontrado.");
            return;
        }

        try {
            palestranteDAO.associarEvento(palestranteId, eventoId);
            System.out.println("Palestrante associado ao evento com sucesso!");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Este palestrante já está associado a este evento.");
            } else {
                System.out.println("Erro ao associar palestrante ao evento: " + e.getMessage());
            }
        }
    }
}