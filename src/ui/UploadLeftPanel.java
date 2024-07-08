package ui;

import static java.lang.StringTemplate.STR;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jetbrains.annotations.NotNull;

import io.event.IONotifier;
import main.App;

public class UploadLeftPanel extends JPanel {
	private JFileChooser fileChooser;
	private JPanel bottomPanel;
	private JList<File> fileJList;
	private UploadPanel uploadPanel;
	
	public UploadLeftPanel(UploadPanel uploadPanel) {
		super();
		this.uploadPanel = uploadPanel;
		
		setLayout(new BorderLayout());
		initFileChooser();
		initBottomPanel();
		initJList();
		initPickAFileButton();
		initConvertButton();
	}
	
	private void initBottomPanel() {
		bottomPanel = new JPanel(new FlowLayout());
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	private void initFileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(null, "gif"));
		fileChooser.setCurrentDirectory(new File(STR."C:\\Users\\\{System.getProperty("user.name")}\\Downloads"));
		fileChooser.setMultiSelectionEnabled(true);
	}
	
	private void initJList() {
		DefaultListModel<File> model = new DefaultListModel<>();
		fileJList = getFileJList(model);
		
		// Update the list on a new file import
		App.getIONotifier().subscribe(event -> {
			var files = event.getFiles();
			
			for (File file : files) {
				if (model.contains(file))
					continue;
				
				model.addElement(file);
			}
			
		}, IONotifier.EventType.FILE_ADDED);
		
		add(new JScrollPane(fileJList));
	}
	
	private @NotNull JList<File> getFileJList(DefaultListModel<File> model) {
		JList<File> fileJList = new JList<>(model);
		
		fileJList.getSelectionModel().addListSelectionListener(e -> {
			uploadPanel.updateDisplayPanel(new ImageIcon(fileJList.getSelectedValue().getPath()));
		});
		
		return fileJList;
	}
	
	private void initPickAFileButton() {
		JButton pickAFileButton = new JButton("Pick a file");
		
		pickAFileButton.addActionListener(e -> {
			var response = fileChooser.showOpenDialog(UploadLeftPanel.this);
			
			if (response != JFileChooser.APPROVE_OPTION)
				return;
			
			File[] selectedFiles = fileChooser.getSelectedFiles();
			App.getInputProcessor().addFiles(selectedFiles);
		});
		
		bottomPanel.add(pickAFileButton);
	}
	
	private void initConvertButton() {
		JButton convertButton = new JButton("Convert selected files");
		convertButton.addActionListener(e -> {
			var files = fileJList.getSelectedValuesList();
			App.getOutputProcessor().process(new ArrayList<>(files));
		});
		
		bottomPanel.add(convertButton);
	}
}
