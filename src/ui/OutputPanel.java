package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import io.event.IONotifier.EventType;
import main.App;

public class OutputPanel extends JPanel {
	private JSplitPane splitPane = new JSplitPane();
	private JPanel displayPanel = new JPanel(new BorderLayout());
	private OutputLeftPanel leftPanel = new OutputLeftPanel(displayPanel);
	
	public OutputPanel() {
		setLayout(new BorderLayout());
		initSplitPane();
		initLeftPanel();
		initDisplayPanel();
	}

	private void initSplitPane() {
		splitPane.setContinuousLayout(true);
		add(splitPane);
	}
	
	private void initLeftPanel() {
		splitPane.setLeftComponent(leftPanel);
	}
	
	private void initDisplayPanel() {
		ScalableLabel label = new ScalableLabel(null);
		displayPanel.add(label);
		splitPane.setRightComponent(displayPanel);
	}
}
