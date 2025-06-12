package projeto.view;

import projeto.dao.ParticipanteDAO;
import projeto.dao.PalestranteDAO;
import projeto.dao.EventoDAO;
import projeto.model.Participante;
import projeto.model.Palestrante;
import projeto.model.Evento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelAssociacoes extends JPanel {
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();
    private final PalestranteDAO palestranteDAO = new PalestranteDAO();
    private final EventoDAO eventoDAO = new EventoDAO();

    public PainelAssociacoes() {
        setLayout(new GridLayout(2, 1, 10, 10));
        add(criarPainelAssociarParticipanteEvento());
        add(criarPainelAssociarPalestranteEvento());
    }

    private JPanel criarPainelAssociarParticipanteEvento() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Associar Participante a Evento"));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblParticipante = new JLabel("Participante:");
        JComboBox<Participante> participanteBox = new JComboBox<>();
        atualizarParticipantes(participanteBox);
        linha1.add(lblParticipante);
        linha1.add(participanteBox);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField(3); idField.setEditable(false);
        JTextField nomeField = new JTextField(10); nomeField.setEditable(false);
        JTextField emailField = new JTextField(15); emailField.setEditable(false);
        JTextField telefoneField = new JTextField(10); telefoneField.setEditable(false);
        linha2.add(new JLabel("ID:")); linha2.add(idField);
        linha2.add(new JLabel("Nome:")); linha2.add(nomeField);
        linha2.add(new JLabel("Email:")); linha2.add(emailField);
        linha2.add(new JLabel("Telefone:")); linha2.add(telefoneField);

        participanteBox.addActionListener(_ -> {
            Participante p = (Participante) participanteBox.getSelectedItem();
            if (p != null) {
                idField.setText(String.valueOf(p.getId()));
                nomeField.setText(p.getNome());
                emailField.setText(p.getEmail());
                telefoneField.setText(p.getTelefone());
            }
        });
        if (participanteBox.getItemCount() > 0) {
            participanteBox.setSelectedIndex(0);
        }

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEvento = new JLabel("Evento:");
        JComboBox<Evento> eventoBox = new JComboBox<>();
        atualizarEventos(eventoBox);
        linha3.add(lblEvento);
        linha3.add(eventoBox);

        JPanel linha4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idEventoField = new JTextField(3); idEventoField.setEditable(false);
        JTextField nomeEventoField = new JTextField(10); nomeEventoField.setEditable(false);
        JTextField dataEventoField = new JTextField(10); dataEventoField.setEditable(false);
        JTextField localEventoField = new JTextField(10); localEventoField.setEditable(false);
        JTextField descricaoEventoField = new JTextField(15); descricaoEventoField.setEditable(false);
        linha4.add(new JLabel("ID:")); linha4.add(idEventoField);
        linha4.add(new JLabel("Nome:")); linha4.add(nomeEventoField);
        linha4.add(new JLabel("Data:")); linha4.add(dataEventoField);
        linha4.add(new JLabel("Local:")); linha4.add(localEventoField);
        linha4.add(new JLabel("Descrição:")); linha4.add(descricaoEventoField);

        eventoBox.addActionListener(_ -> {
            Evento ev = (Evento) eventoBox.getSelectedItem();
            if (ev != null) {
                idEventoField.setText(String.valueOf(ev.getId()));
                nomeEventoField.setText(ev.getNome());
                dataEventoField.setText(ev.getData() != null ? ev.getData().toString() : "");
                localEventoField.setText(ev.getLocal());
                descricaoEventoField.setText(ev.getDescricao());
            }
        });
        if (eventoBox.getItemCount() > 0) {
            eventoBox.setSelectedIndex(0);
        }

        JPanel linha5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAssociar = new JButton("Associar Participante ao Evento");
        linha5.add(btnAssociar);

        btnAssociar.addActionListener(_ -> {
            Participante p = (Participante) participanteBox.getSelectedItem();
            Evento ev = (Evento) eventoBox.getSelectedItem();
            if (p == null || ev == null) {
                JOptionPane.showMessageDialog(this, "Selecione um participante e um evento.");
                return;
            }
            try {
                participanteDAO.associarEvento(p.getId(), ev.getId());
                JOptionPane.showMessageDialog(this, "Participante associado ao evento com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao associar: " + ex.getMessage());
            }
        });

        panel.add(linha1);
        panel.add(linha2);
        panel.add(linha3);
        panel.add(linha4);
        panel.add(linha5);
        return panel;
    }

    private JPanel criarPainelAssociarPalestranteEvento() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Associar Palestrante a Evento"));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblPalestrante = new JLabel("Palestrante:");
        JComboBox<Palestrante> palestranteBox = new JComboBox<>();
        atualizarPalestrantes(palestranteBox);
        linha1.add(lblPalestrante);
        linha1.add(palestranteBox);

        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idField = new JTextField(3); idField.setEditable(false);
        JTextField nomeField = new JTextField(10); nomeField.setEditable(false);
        JTextField curriculoField = new JTextField(15); curriculoField.setEditable(false);
        JTextField areaAtuacaoField = new JTextField(10); areaAtuacaoField.setEditable(false);
        linha2.add(new JLabel("ID:")); linha2.add(idField);
        linha2.add(new JLabel("Nome:")); linha2.add(nomeField);
        linha2.add(new JLabel("Currículo:")); linha2.add(curriculoField);
        linha2.add(new JLabel("Área de Atuação:")); linha2.add(areaAtuacaoField);

        palestranteBox.addActionListener(_ -> {
            Palestrante p = (Palestrante) palestranteBox.getSelectedItem();
            if (p != null) {
                idField.setText(String.valueOf(p.getId()));
                nomeField.setText(p.getNome());
                curriculoField.setText(p.getCurriculo());
                areaAtuacaoField.setText(p.getAreaAtuacao());
            }
        });
        if (palestranteBox.getItemCount() > 0) {
            palestranteBox.setSelectedIndex(0);
        }

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblEvento = new JLabel("Evento:");
        JComboBox<Evento> eventoBox = new JComboBox<>();
        atualizarEventos(eventoBox);
        linha3.add(lblEvento);
        linha3.add(eventoBox);

        JPanel linha4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField idEventoField = new JTextField(3); idEventoField.setEditable(false);
        JTextField nomeEventoField = new JTextField(10); nomeEventoField.setEditable(false);
        JTextField dataEventoField = new JTextField(10); dataEventoField.setEditable(false);
        JTextField localEventoField = new JTextField(10); localEventoField.setEditable(false);
        JTextField descricaoEventoField = new JTextField(15); descricaoEventoField.setEditable(false);
        linha4.add(new JLabel("ID:")); linha4.add(idEventoField);
        linha4.add(new JLabel("Nome:")); linha4.add(nomeEventoField);
        linha4.add(new JLabel("Data:")); linha4.add(dataEventoField);
        linha4.add(new JLabel("Local:")); linha4.add(localEventoField);
        linha4.add(new JLabel("Descrição:")); linha4.add(descricaoEventoField);

        eventoBox.addActionListener(_ -> {
            Evento ev = (Evento) eventoBox.getSelectedItem();
            if (ev != null) {
                idEventoField.setText(String.valueOf(ev.getId()));
                nomeEventoField.setText(ev.getNome());
                dataEventoField.setText(ev.getData() != null ? ev.getData().toString() : "");
                localEventoField.setText(ev.getLocal());
                descricaoEventoField.setText(ev.getDescricao());
            }
        });
        if (eventoBox.getItemCount() > 0) {
            eventoBox.setSelectedIndex(0);
        }

        JPanel linha5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAssociar = new JButton("Associar Palestrante ao Evento");
        linha5.add(btnAssociar);

        btnAssociar.addActionListener(_ -> {
            Palestrante p = (Palestrante) palestranteBox.getSelectedItem();
            Evento ev = (Evento) eventoBox.getSelectedItem();
            if (p == null || ev == null) {
                JOptionPane.showMessageDialog(this, "Selecione um palestrante e um evento.");
                return;
            }
            try {
                palestranteDAO.associarEvento(p.getId(), ev.getId());
                JOptionPane.showMessageDialog(this, "Palestrante associado ao evento com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao associar: " + ex.getMessage());
            }
        });

        panel.add(linha1);
        panel.add(linha2);
        panel.add(linha3);
        panel.add(linha4);
        panel.add(linha5);
        return panel;
    }

    private void atualizarParticipantes(JComboBox<Participante> box) {
        box.removeAllItems();
        List<Participante> participantes = participanteDAO.listar();
        for (Participante p : participantes) {
            box.addItem(p);
        }
    }

    private void atualizarPalestrantes(JComboBox<Palestrante> box) {
        box.removeAllItems();
        try {
            List<Palestrante> palestrantes = palestranteDAO.listarTodos();
            for (Palestrante p : palestrantes) {
                box.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar palestrantes: " + e.getMessage());
        }
    }

    private void atualizarEventos(JComboBox<Evento> box) {
        box.removeAllItems();
        try {
            List<Evento> eventos = eventoDAO.listarTodos();
            for (Evento e : eventos) {
                box.addItem(e);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + e.getMessage());
        }
    }
}
