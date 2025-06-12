package projeto.view;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import projeto.model.Evento;
import projeto.model.Participante;

public class TelaCertificado extends JFrame {
    public TelaCertificado(Participante participante, Evento evento) {
        setTitle("Certificado de Participação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(220, 220, 255));
                g2.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 30, 30);
                g2.setColor(new Color(100, 100, 200));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 30, 30);
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("CERTIFICADO DE PARTICIPAÇÃO", JLabel.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(new Color(60, 60, 120));
        panel.add(Box.createVerticalStrut(30));
        panel.add(titulo);

        panel.add(Box.createVerticalStrut(30));
        JLabel texto = new JLabel("Certificamos que ", JLabel.CENTER);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 16));
        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(texto);

        JLabel nome = new JLabel(participante.getNome(), JLabel.CENTER);
        nome.setFont(new Font("SansSerif", Font.BOLD, 18));
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);
        nome.setForeground(new Color(30, 30, 100));
        panel.add(nome);

        JLabel texto2 = new JLabel("participou do evento", JLabel.CENTER);
        texto2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        texto2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(texto2);

        JLabel nomeEvento = new JLabel("\"" + evento.getNome() + "\"", JLabel.CENTER);
        nomeEvento.setFont(new Font("SansSerif", Font.BOLD, 17));
        nomeEvento.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomeEvento.setForeground(new Color(30, 30, 100));
        panel.add(nomeEvento);

        JLabel data = new JLabel("Realizado em: " + evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), JLabel.CENTER);
        data.setFont(new Font("SansSerif", Font.PLAIN, 15));
        data.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(data);

        panel.add(Box.createVerticalStrut(30));
        JLabel assinatura = new JLabel("____________________________", JLabel.CENTER);
        assinatura.setFont(new Font("SansSerif", Font.PLAIN, 14));
        assinatura.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(assinatura);
        JLabel org = new JLabel("Organização do Evento", JLabel.CENTER);
        org.setFont(new Font("SansSerif", Font.ITALIC, 13));
        org.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(org);

        add(panel);
    }
}
