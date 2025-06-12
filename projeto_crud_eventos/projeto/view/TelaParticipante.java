package projeto.view;

import projeto.dao.EventoDAO;
import projeto.model.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaParticipante extends JFrame {
    private final EventoDAO eventoDAO = new EventoDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final String nomeParticipante;

    public TelaParticipante(String nomeParticipante) {
        this.nomeParticipante = nomeParticipante;
        setTitle("Área do Participante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Bem-vindo, " + nomeParticipante + "! Eventos Disponíveis:", JLabel.CENTER);
        add(label, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Data", "Local", "Descrição"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnInscrever = new JButton("Inscrever-se no Evento Selecionado");
        JButton btnVerLocal = new JButton("Ver Local do Evento");
        JButton btnVoltarLista = new JButton("Voltar à Lista de Eventos");
        JButton btnSolicitarCertificado = new JButton("Solicitar Certificado");
        JButton btnCadastrar = new JButton("Cadastrar Participante");
        buttonPanel.add(btnInscrever);
        buttonPanel.add(btnVerLocal);
        buttonPanel.add(btnVoltarLista);
        buttonPanel.add(btnSolicitarCertificado);
        buttonPanel.add(btnCadastrar);
        add(buttonPanel, BorderLayout.SOUTH);

        btnInscrever.addActionListener(_ -> inscreverNoEvento());
        btnVerLocal.addActionListener(_ -> verLocalEvento());
        btnVoltarLista.addActionListener(_ -> atualizarTabela());
        btnSolicitarCertificado.addActionListener(_ -> solicitarCertificado());
        btnCadastrar.addActionListener(_ -> cadastrarParticipante());

        atualizarTabela();
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

    private void inscreverNoEvento() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para se inscrever.");
            return;
        }
        int idEvento = (int) tableModel.getValueAt(row, 0);
        try {
            projeto.dao.ParticipanteDAO participanteDAO = new projeto.dao.ParticipanteDAO();
            // Busca o participante pelo nome (simples, para exemplo)
            java.util.List<projeto.model.Participante> participantes = participanteDAO.listar();
            projeto.model.Participante participante = participantes.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nomeParticipante))
                .findFirst().orElse(null);
            if (participante == null) {
                JOptionPane.showMessageDialog(this, "Participante não encontrado para inscrição.");
                return;
            }
            participanteDAO.associarEvento(participante.getId(), idEvento);
            JOptionPane.showMessageDialog(this, "Inscrição realizada para o evento.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao inscrever: " + ex.getMessage());
        }
    }

    private void verLocalEvento() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para ver o local.");
            return;
        }
        String local = (String) tableModel.getValueAt(row, 3);
        JOptionPane.showMessageDialog(this, "O evento será realizado em: " + local);
    }

    private void cadastrarParticipante() {
        String nome = JOptionPane.showInputDialog(this, "Nome:");
        String email = JOptionPane.showInputDialog(this, "Email:");
        String telefone = JOptionPane.showInputDialog(this, "Telefone:");
        if (nome == null || email == null || telefone == null || nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos para cadastro.");
            return;
        }
        try {
            projeto.dao.ParticipanteDAO participanteDAO = new projeto.dao.ParticipanteDAO();
            participanteDAO.inserir(new projeto.model.Participante(0, nome, email, telefone));
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar participante: " + ex.getMessage());
        }
    }

    private void solicitarCertificado() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para solicitar o certificado.");
            return;
        }
        java.time.LocalDate dataEvento = (java.time.LocalDate) tableModel.getValueAt(row, 2);
        if (dataEvento.isAfter(java.time.LocalDate.now())) {
            JOptionPane.showMessageDialog(this, "O certificado só pode ser solicitado após a realização do evento.");
            return;
        }
        // Buscar participante completo pelo nome
        projeto.dao.ParticipanteDAO participanteDAO = new projeto.dao.ParticipanteDAO();
        java.util.List<projeto.model.Participante> participantes = participanteDAO.listar();
        projeto.model.Participante participante = participantes.stream()
            .filter(p -> p.getNome().equalsIgnoreCase(nomeParticipante))
            .findFirst().orElse(null);
        if (participante == null) {
            JOptionPane.showMessageDialog(this, "Participante não encontrado para emissão de certificado.");
            return;
        }
        // Buscar evento completo pelo nome e data
        int idEvento = (int) tableModel.getValueAt(row, 0);
        projeto.model.Evento evento = null;
        try {
            List<projeto.model.Evento> eventos = eventoDAO.listarTodos();
            evento = eventos.stream().filter(e -> e.getId() == idEvento).findFirst().orElse(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar evento: " + ex.getMessage());
            return;
        }
        if (evento == null) {
            JOptionPane.showMessageDialog(this, "Evento não encontrado para emissão de certificado.");
            return;
        }
        // Exibir tela visual de certificado
        new TelaCertificado(participante, evento).setVisible(true);
    }
}
