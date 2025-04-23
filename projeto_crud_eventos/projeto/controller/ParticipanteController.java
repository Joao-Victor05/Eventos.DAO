package projeto.controller;

import projeto.model.Participante;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParticipanteController {
    private List<Participante> participantes;
    private static final Scanner scanner = new Scanner(System.in);

    public ParticipanteController() {
        participantes = new ArrayList<>();
    }

    // Adicionar participante
    public Participante adicionarParticipante() {
        try {
            System.out.println("\n--- Adicionar novo participante ---");

            // ID gerado automaticamente, então removemos a solicitação para o ID
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();
            while (!validarEmail(email)) {
                System.out.print("Email inválido. Digite novamente: ");
                email = scanner.nextLine();
            }

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();
            while (!validarTelefone(telefone)) {
                System.out.print("Telefone inválido. Digite novamente: ");
                telefone = scanner.nextLine();
            }

            // O ID pode ser gerado automaticamente pelo sistema (ou banco de dados)
            Participante novo = new Participante(0, nome, email, telefone); 
            participantes.add(novo);

            System.out.println("Participante adicionado com sucesso!");
            return novo;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar participante: " + e.getMessage());
            return null;
        }
    }

    // Atualizar participante por ID
    public boolean atualizarParticipante(int id, Participante novoParticipante) {
        for (int i = 0; i < participantes.size(); i++) {
            if (participantes.get(i).getId() == id) {
                novoParticipante.setId(id); // Manter o mesmo ID
                participantes.set(i, novoParticipante);
                System.out.println("Participante atualizado com sucesso!");
                return true;
            }
        }
        System.out.println("Participante com ID " + id + " não encontrado.");
        return false;
    }

    // Remover participante por ID
    public boolean removerParticipante(int id) {
        for (int i = 0; i < participantes.size(); i++) {
            if (participantes.get(i).getId() == id) {
                participantes.remove(i);
                System.out.println("Participante removido com sucesso!");
                return true;
            }
        }
        System.out.println("Participante com ID " + id + " não encontrado.");
        return false;
    }

    // Listar todos os participantes
    public List<Participante> listarParticipantes() {
        if (participantes.isEmpty()) {
            System.out.println("Nenhum participante cadastrado.");
        } else {
            System.out.println("\n--- Lista de Participantes ---");
            for (Participante p : participantes) {
                System.out.println(p);
            }
        }
        return participantes;
    }

    // Buscar por ID
    public Participante buscarParticipantePorId(int id) {
        for (Participante p : participantes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Validação simples de e-mail
    private boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Validação simples de telefone
    private boolean validarTelefone(String telefone) {
        return telefone != null && telefone.matches("\\(\\d{2}\\) \\d{5}-\\d{4}"); // Exemplo: (11) 98765-4321
    }
}
