package projeto.controller;

 import java.time.LocalDate;
 import java.time.format.DateTimeParseException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 import projeto.model.Evento;

    public class EventoController {
     private List<Evento> eventos;
     private static final Scanner scanner = new Scanner(System.in);

    public EventoController() {
        eventos = new ArrayList<>();
    }

   
    public Evento adicionarEvento() {
        try {
            System.out.println("\nInserir novo evento:");

            
            System.out.print("Nome: ");
            String nomeInserir = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricaoInserir = scanner.nextLine();

            System.out.print("Data (yyyy-MM-dd): ");
            String dataStr = scanner.nextLine();
            LocalDate dataInserir = LocalDate.parse(dataStr);

            if (dataInserir.isBefore(LocalDate.now())) {
                System.out.println("Data inválida! O evento não pode ser no passado.");
                return null;
            }

            System.out.print("Local: ");
            String localInserir = scanner.nextLine();

            
            Evento novoEvento = new Evento(0, nomeInserir, dataInserir, localInserir, descricaoInserir);
            eventos.add(novoEvento);

            System.out.println("Evento adicionado com sucesso!");
            return novoEvento;

        } catch (DateTimeParseException e) {
            System.out.println("Erro: Data inválida. Use o formato yyyy-MM-dd.");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar evento: " + e.getMessage());
        }
        return null;
    }

   
    public boolean atualizarEvento(int id, Evento novoEvento) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId() == id) {
                novoEvento.setId(id);
                eventos.set(i, novoEvento);
                System.out.println("Evento atualizado com sucesso!");
                return true;
            }
        }
        System.out.println("Evento com ID " + id + " não encontrado.");
        return false;
    }

    
    public boolean removerEvento(int id) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getId() == id) {
                eventos.remove(i);
                System.out.println("Evento removido com sucesso!");
                return true;
            }
        }
        System.out.println("Evento com ID " + id + " não encontrado.");
        return false;
    }

    
    public void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            System.out.println("\n--- Lista de Eventos ---");
            for (Evento evento : eventos) {
                System.out.println(evento);
            }
        }
    }

    
    public Evento buscarEventoPorId(int id) {
        for (Evento evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }
        return null;
    }
}
