package projeto.controller;

 import projeto.model.Palestrante;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;

    public class PalestranteController {
    private List<Palestrante> palestrantes;
    private static final Scanner scanner = new Scanner(System.in);

    public PalestranteController() {
        palestrantes = new ArrayList<>();
    }

 
    public Palestrante adicionarPalestrante() {
        try {
            System.out.println("\n--- Adicionar novo palestrante ---");

            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Currículo: ");
            String curriculo = scanner.nextLine();

            System.out.print("Área de Atuação: ");
            String areaAtuacao = scanner.nextLine();

        
            if (nome.isEmpty() || curriculo.isEmpty() || areaAtuacao.isEmpty()) {
                System.out.println("Todos os campos são obrigatórios.");
                return null;
            }

          
            Palestrante novo = new Palestrante(0, nome, curriculo, areaAtuacao);
            palestrantes.add(novo);

            System.out.println("Palestrante adicionado com sucesso!");
            return novo;

        } catch (Exception e) {
            System.out.println("Erro ao adicionar palestrante: " + e.getMessage());
            return null;
        }
    }

    
    public boolean atualizarPalestrante(int id, Palestrante novoPalestrante) {
        for (int i = 0; i < palestrantes.size(); i++) {
            if (palestrantes.get(i).getId() == id) {
                novoPalestrante.setId(id); // Manter o mesmo ID
                palestrantes.set(i, novoPalestrante);
                System.out.println("Palestrante atualizado com sucesso!");
                return true;
            }
        }
        System.out.println("Palestrante com ID " + id + " não encontrado.");
        return false;
    }

   
    public boolean removerPalestrante(int id) {
        for (int i = 0; i < palestrantes.size(); i++) {
            if (palestrantes.get(i).getId() == id) {
                palestrantes.remove(i);
                System.out.println("Palestrante removido com sucesso!");
                return true;
            }
        }
        System.out.println("Palestrante com ID " + id + " não encontrado.");
        return false;
    }

   
    public List<Palestrante> listarPalestrantes() {
        if (palestrantes.isEmpty()) {
            System.out.println("Nenhum palestrante cadastrado.");
        } else {
            System.out.println("\n--- Lista de Palestrantes ---");
            for (Palestrante p : palestrantes) {
                System.out.println(p);
            }
        }
        return palestrantes;
    }

   
    public Palestrante buscarPalestrantePorId(int id) {
        for (Palestrante p : palestrantes) {
            if (p.getId() == id) {
                return p;
            }
        }
        System.out.println("Palestrante com ID " + id + " não encontrado.");
        return null;
    }
}
