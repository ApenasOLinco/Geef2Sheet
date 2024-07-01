package ui;

import javax.swing.*;

public class AppWindow extends JFrame {
    private final MainPanel panel;

    public AppWindow() {
        super("AppWindow");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) { e.printStackTrace(); }

        panel = new MainPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}