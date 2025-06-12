package projeto.view;

import projeto.dao.PalestranteDAO;
import projeto.model.Palestrante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelPalestrantes extends JPanel {
    private final PalestranteDAO palestranteDAO = new PalestranteDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PainelPalestrantes() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Cadastro e Listagem de Palestrantes", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        // Tabela de palestrantes
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Currículo", "Área de Atuação"}, 0) {
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
        JTextField curriculoField = new JTextField();
        JTextField areaAtuacaoField = new JTextField();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar Selecionado");
        JButton btnRemover = new JButton("Remover Selecionado");
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Currículo:"));
        formPanel.add(curriculoField);
        formPanel.add(new JLabel("Área de Atuação:"));
        formPanel.add(areaAtuacaoField);
        formPanel.add(btnAdicionar);
        formPanel.add(btnEditar);
        formPanel.add(btnRemover);
        add(formPanel, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(_ -> adicionarPalestrante(nomeField, curriculoField, areaAtuacaoField));
        btnEditar.addActionListener(_ -> editarPalestrante());
        btnRemover.addActionListener(_ -> removerPalestrante());

        atualizarTabela();
    }

    private void adicionarPalestrante(JTextField nomeField, JTextField curriculoField, JTextField areaAtuacaoField) {
        try {
            String nome = nomeField.getText().trim();
            String curriculo = curriculoField.getText().trim();
            String areaAtuacao = areaAtuacaoField.getText().trim();
            if (nome.isEmpty() || curriculo.isEmpty() || areaAtuacao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }
            Palestrante palestrante = new Palestrante(0, nome, curriculo, areaAtuacao);
            palestranteDAO.inserir(palestrante);
            atualizarTabela();
            nomeField.setText("");
            curriculoField.setText("");
            areaAtuacaoField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar palestrante: " + ex.getMessage());
        }
    }

    private void editarPalestrante() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para editar.");
            return;
        }
        try {
            int id = (int) tableModel.getValueAt(row, 0);
            String nome = JOptionPane.showInputDialog(this, "Novo nome:", tableModel.getValueAt(row, 1));
            String curriculo = JOptionPane.showInputDialog(this, "Novo currículo:", tableModel.getValueAt(row, 2));
            String areaAtuacao = JOptionPane.showInputDialog(this, "Nova área de atuação:", tableModel.getValueAt(row, 3));
            if (nome == null || curriculo == null || areaAtuacao == null) return;
            Palestrante palestrante = new Palestrante(id, nome, curriculo, areaAtuacao);
            palestranteDAO.atualizar(palestrante);
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar palestrante: " + ex.getMessage());
        }
    }

    private void removerPalestrante() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um palestrante para remover.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover o palestrante?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                palestranteDAO.excluir(id);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover palestrante: " + ex.getMessage());
            }
        }
    }

    private void atualizarTabela() {
        try {
            tableModel.setRowCount(0);
            List<Palestrante> palestrantes = palestranteDAO.listarTodos();
            for (Palestrante p : palestrantes) {
                tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getCurriculo(), p.getAreaAtuacao()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar palestrantes: " + ex.getMessage());
        }
    }
}
