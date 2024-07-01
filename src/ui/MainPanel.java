package ui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final int width = 800, height = 600;
    private final JTabbedPane tabs = new JTabbedPane();
    private JPanel uploadPanel, optionsPanel;

    public MainPanel() {
        initSizes();
        initPanels();
        setLayout(new BorderLayout());
        add(tabs);
        tabs.setTabPlacement(JTabbedPane.BOTTOM);
        tabs.addTab("Upload", uploadPanel);
        tabs.addTab("Options", optionsPanel);
    }

    private void initPanels() {
        initUploadPanel();
        initOptionsPanel();
    }

    private void initUploadPanel() {
        uploadPanel = new UploadPanel();
    }

    private void initOptionsPanel() {
        optionsPanel = new JPanel();
        optionsPanel.setLayout(getLayout());
        optionsPanel.add(new JLabel("Options Panel"), BorderLayout.NORTH);
    }

    private void initSizes() {
        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
    }
}
