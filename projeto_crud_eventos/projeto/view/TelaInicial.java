package projeto.view;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("Sistema de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel label = new JLabel("Bem-vindo ao Sistema de Eventos", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton btnAdmin = new JButton("Entrar como Administrador");
        panel.add(btnAdmin, gbc);

        gbc.gridx = 1;
        JButton btnParticipante = new JButton("Entrar como Participante");
        panel.add(btnParticipante, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel info = new JLabel("Escolha o tipo de acesso desejado.", JLabel.CENTER);
        panel.add(info, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel dicaAtalhos = new JLabel("Atalhos: Alt+A (Administrador), Alt+P (Participante)", JLabel.CENTER);
        dicaAtalhos.setFont(new Font("Arial", Font.ITALIC, 12));
        panel.add(dicaAtalhos, gbc);

        add(panel);

        btnAdmin.addActionListener(_ -> {
            this.dispose();
            new TelaLoginAdmin().setVisible(true);
        });
        btnParticipante.addActionListener(_ -> {
            this.dispose();
            new TelaLoginParticipante().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
