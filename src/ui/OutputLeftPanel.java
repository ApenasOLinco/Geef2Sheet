package ui;

import java.awt.BorderLayout;
import java.io.File;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;

import io.event.IONotifier.EventType;
import main.App;

public class OutputLeftPanel extends JPanel {
	JList<String> outputJList = new JList<>();
	JPanel displayPanel;
	
	public OutputLeftPanel(JPanel displayPanel) {
		this.displayPanel = displayPanel;
		setLayout(new BorderLayout());
		initJList();
	}

	private void initJList() {
		// TODO Terminar a implementação 
		DefaultListModel<String> model = new DefaultListModel<>();
		outputJList.setModel(model);
		
		outputJList.addListSelectionListener(e -> {
			if(e.getValueIsAdjusting()) return; // Stops double calls from mouse input
			
			ScalableLabel label = (ScalableLabel) displayPanel.getComponent(0);
			HashMap<String, File> cachedOutputFiles = App.getFileManager().getCachedOutputFiles();
			
			String fileName = outputJList.getSelectedValue();
			label.setIcon(new ImageIcon(cachedOutputFiles.get(fileName).getPath()));
		});
		
		App.getIONotifier().subscribe(event -> {
			for(File file : event.getFiles()) {
				model.addElement(file.getName());
			}
			
			App.getAppWindow().queueRepaint(outputJList);
		}, EventType.FILES_PROCESSED);
		
		add(outputJList, BorderLayout.CENTER);
	}
}
