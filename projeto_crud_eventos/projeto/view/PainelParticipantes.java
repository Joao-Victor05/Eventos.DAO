package projeto.view;

import projeto.dao.ParticipanteDAO;
import projeto.model.Participante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelParticipantes extends JPanel {
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PainelParticipantes() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Cadastro e Listagem de Participantes", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        // Tabela de participantes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Formulário de cadastro
        JPanel formPanel = new JPanel(new GridLayout(2, 5));
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnRemover = new JButton("Remover Selecionado");
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(telefoneField);
        formPanel.add(btnAdicionar);
        formPanel.add(btnEditar);
        formPanel.add(btnRemover);
        add(formPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(_ -> adicionarParticipante(nomeField, emailField, telefoneField));
        btnEditar.addActionListener(_ -> editarParticipante());
        btnRemover.addActionListener(_ -> removerParticipante());

        atualizarTabela();
    }

    private void adicionarParticipante(JTextField nomeField, JTextField emailField, JTextField telefoneField) {
        try {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String telefone = telefoneField.getText().trim();
            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }
            Participante participante = new Participante(0, nome, email, telefone);
            participanteDAO.inserir(participante);
            atualizarTabela();
            nomeField.setText("");
            emailField.setText("");
            telefoneField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar participante: " + ex.getMessage());
        }
    }

    private void editarParticipante() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um participante para editar.");
            return;
        }
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            String nome = JOptionPane.showInputDialog(this, "Novo nome:", tableModel.getValueAt(row, 1));
            String email = JOptionPane.showInputDialog(this, "Novo email:", tableModel.getValueAt(row, 2));
            String telefone = JOptionPane.showInputDialog(this, "Novo telefone:", tableModel.getValueAt(row, 3));
            if (nome == null || email == null || telefone == null) return;
            Participante participante = new Participante(id, nome, email, telefone);
            participanteDAO.atualizar(participante);
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar participante: " + ex.getMessage());
        }
    }

    private void removerParticipante() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um participante para remover.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o participante?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                participanteDAO.excluir(id);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover participante: " + ex.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        try {
            tableModel.setRowCount(0);
            List<Participante> participantes = participanteDAO.listar();
            for (Participante p : participantes) {
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getEmail(), p.getTelefone()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar participantes: " + ex.getMessage());
        }
    }
}
