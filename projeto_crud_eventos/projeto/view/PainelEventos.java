package projeto.view;

import projeto.dao.EventoDAO;
import projeto.model.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PainelEventos extends JPanel {
    private final EventoDAO eventoDAO = new EventoDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PainelEventos() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Cadastro e Listagem de Eventos", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        // Tabela de eventos
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Data", "Local", "Descrição"}, 0) {
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
        JTextField dataField = new JTextField("AAAA-MM-DD");
        JTextField localField = new JTextField();
        JTextField descricaoField = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnRemover = new JButton("Remover Selecionado");
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Data:"));
        formPanel.add(dataField);
        formPanel.add(new JLabel("Local:"));
        formPanel.add(localField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descricaoField);
        formPanel.add(btnAdicionar);
        formPanel.add(btnEditar);
        formPanel.add(btnRemover);
        add(formPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(_ -> adicionarEvento(nomeField, dataField, localField, descricaoField));
        btnEditar.addActionListener(_ -> editarEvento());
        btnRemover.addActionListener(_ -> removerEvento());

        atualizarTabela();
    }

    private void adicionarEvento(JTextField nomeField, JTextField dataField, JTextField localField, JTextField descricaoField) {
        try {
            String nome = nomeField.getText().trim();
            String dataStr = dataField.getText().trim();
            String local = localField.getText().trim();
            String descricao = descricaoField.getText().trim();
            if (nome.isEmpty() || dataStr.isEmpty() || local.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }
            LocalDate data = LocalDate.parse(dataStr);
            Evento evento = new Evento(nome, data, local, descricao);
            eventoDAO.inserir(evento);
            atualizarTabela();
            nomeField.setText("");
            dataField.setText("");
            localField.setText("");
            descricaoField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar evento: " + ex.getMessage());
        }
    }

    private void editarEvento() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para editar.");
            return;
        }
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            String nome = JOptionPane.showInputDialog(this, "Novo nome:", tableModel.getValueAt(row, 1));
            String dataStr = JOptionPane.showInputDialog(this, "Nova data (AAAA-MM-DD):", tableModel.getValueAt(row, 2));
            String local = JOptionPane.showInputDialog(this, "Novo local:", tableModel.getValueAt(row, 3));
            String descricao = JOptionPane.showInputDialog(this, "Nova descrição:", tableModel.getValueAt(row, 4));
            if (nome == null || dataStr == null || local == null || descricao == null) return;
            LocalDate data = LocalDate.parse(dataStr);
            Evento evento = new Evento(id, nome, data, local, descricao);
            eventoDAO.atualizar(evento);
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar evento: " + ex.getMessage());
        }
    }

    private void removerEvento() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para remover.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o evento?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                eventoDAO.excluir(id);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover evento: " + ex.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        try {
            tableModel.setRowCount(0);
            List<Evento> eventos = eventoDAO.listarTodos();
            for (Evento e : eventos) {
                tableModel.addRow(new Object[]{e.getId(), e.getNome(), e.getData(), e.getLocal(), e.getDescricao()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar eventos: " + ex.getMessage());
        }
    }
}
