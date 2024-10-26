package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;

import io.event.IONotifier.EventType;
import main.App;

public class OutputLeftPanel extends JPanel {
	JList<File> outputJList = new JList<>();
	JPanel displayPanel;
	
	public OutputLeftPanel(JPanel displayPanel) {
		this.displayPanel = displayPanel;
		setLayout(new BorderLayout());
		initJList();
	}

	private void initJList() {
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
		
		add(outputJList, BorderLayout.CENTER);
	}
}
