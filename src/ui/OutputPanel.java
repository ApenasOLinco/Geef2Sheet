package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	private JList<File> outputJList = new JList<>();
	private JPanel displayPanel = new JPanel(new BorderLayout());
	
	public OutputPanel() {
		setLayout(new BorderLayout());
		initSplitPane();
		initOutputJList();
		initDisplayPanel();
	}

	private void initOutputJList() {
		DefaultListModel<File> model = new DefaultListModel<>();
		outputJList.setModel(model);
		
		outputJList.addListSelectionListener(e -> {
			ScalableLabel label = (ScalableLabel) displayPanel.getComponent(0);
			label.setIcon(new ImageIcon(outputJList.getSelectedValue().getPath()));
		});
		
		App.getIONotifier().subscribe(event -> {
			for(File file : event.getFiles()) {
				model.addElement(file);
			}
			
			App.getAppWindow().queueRepaint(outputJList);
			outputJList.revalidate();
		}, EventType.FILES_PROCESSED);
		
		splitPane.setLeftComponent(outputJList);
	}

	private void initSplitPane() {
		splitPane.setContinuousLayout(true);
		add(splitPane);
	}
	
	private void initDisplayPanel() {
		ScalableLabel label = new ScalableLabel(null);
		label.setMinimumSize(new Dimension(32, 32));
		displayPanel.add(label );
		splitPane.setRightComponent(displayPanel);
	}
}
