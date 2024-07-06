package ui;

import java.awt.Component;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class AppWindow extends JFrame {
    private final MainPanel panel;
    
    private final HashSet<Component> componentsToRepaint = new HashSet<>();
    
    public AppWindow() {
        super("AppWindow");

        try {
            System.out.println(Arrays.toString(UIManager.getInstalledLookAndFeels()));
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) { e.printStackTrace(); }

        panel = new MainPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
    
    public void queueRepaint(Component comp) {
    	componentsToRepaint.add(comp);
    }
    
    public void repaintQueuedComponents() {
    	componentsToRepaint.forEach(comp -> comp.repaint());
    	componentsToRepaint.clear();
    }
}