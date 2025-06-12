package projeto.view;

import javax.swing.*;
import java.awt.*;

public class TelaLoginAdmin extends JFrame {
    public TelaLoginAdmin() {
        setTitle("Login Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblUsuario = new JLabel("Usuário:");
        JTextField txtUsuario = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField();
        JButton btnEntrar = new JButton("Entrar");

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblSenha);
        panel.add(txtSenha);
        panel.add(new JLabel());
        panel.add(btnEntrar);
        add(panel);

        btnEntrar.addActionListener(_ -> {
            String usuario = txtUsuario.getText().trim();
            String senha = new String(txtSenha.getPassword());
            if (usuario.equals("admin") && senha.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                this.dispose();
                new TelaPrincipal().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLoginAdmin().setVisible(true));
    }
}
