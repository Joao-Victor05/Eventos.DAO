package projeto.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Eventos", new PainelEventos());
        tabbedPane.addTab("Participantes", new PainelParticipantes());
        tabbedPane.addTab("Palestrantes", new PainelPalestrantes());
        tabbedPane.addTab("Associações", new PainelAssociacoes());
        
        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
