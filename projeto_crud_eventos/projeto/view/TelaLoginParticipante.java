package projeto.view;

import projeto.dao.ParticipanteDAO;
import projeto.model.Participante;

import javax.swing.*;
import java.awt.*;

public class TelaLoginParticipante extends JFrame {
    private final ParticipanteDAO participanteDAO = new ParticipanteDAO();

    public TelaLoginParticipante() {
        setTitle("Cadastro de Participante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel lblTelefone = new JLabel("Telefone:");
        JTextField txtTelefone = new JTextField();
        JLabel lblSenha = new JLabel("Senha (máx. 8 caracteres):");
        JPasswordField txtSenha = new JPasswordField();
        JButton btnCadastrar = new JButton("Cadastrar e Entrar");

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblTelefone);
        panel.add(txtTelefone);
        panel.add(lblSenha);
        panel.add(txtSenha);
        panel.add(new JLabel());
        panel.add(btnCadastrar);
        add(panel);

        btnCadastrar.addActionListener(_ -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String senha = new String(txtSenha.getPassword());
            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }
            if (senha.length() > 8) {
                JOptionPane.showMessageDialog(this, "A senha deve ter no máximo 8 caracteres.");
                return;
            }
            try {
                participanteDAO.inserir(new Participante(0, nome, email, telefone));
                JOptionPane.showMessageDialog(this, "Cadastro realizado! Bem-vindo, " + nome + ".");
                this.dispose();
                new TelaParticipante(nome).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLoginParticipante().setVisible(true));
    }
}
