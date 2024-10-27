package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class OutputPanel extends JPanel {
	private JSplitPane splitPane = new JSplitPane();
	private JPanel displayPanel = new JPanel(new BorderLayout());
	private OutputLeftPanel leftPanel = new OutputLeftPanel(displayPanel);
	
	public OutputPanel(Dimension size) {
		setSize(size);
		setLayout(new BorderLayout());
		initSplitPane();
		initLeftPanel();
		initDisplayPanel();
	}

	private void initSplitPane() {
		splitPane.setContinuousLayout(true);
		splitPane.setSize(getSize());
		splitPane.setDividerLocation(.33);
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
