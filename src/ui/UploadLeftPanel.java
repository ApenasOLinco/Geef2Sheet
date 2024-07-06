package ui;

import static java.lang.StringTemplate.STR;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jetbrains.annotations.NotNull;

import io.event.InputNotifier;
import io.event.InputNotifier.EventType;
import main.App;

public class UploadLeftPanel extends JPanel {
	private JFileChooser fileChooser;
	private JPanel bottomPanel;

	public UploadLeftPanel() {
		super();

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
		final JList<File> fileJList = getFileJList(model);

		// Update the list on a new file import
		App.getInputManager().getInputNotifier().subscribe(event-> {
			var files = event.getFiles();
			
			for (File file : files) {
				if (model.contains(file))
					continue;

				model.addElement(file);
			}

		}, InputNotifier.EventType.FILE_ADDED);

		add(new JScrollPane(fileJList));
	}

	private @NotNull JList<File> getFileJList(DefaultListModel<File> model) {
		JList<File> fileJList = new JList<>(model);

		fileJList.getSelectionModel().addListSelectionListener(e -> {
			App.getInputManager().getInputNotifier().notifyObservers(EventType.JLIST_FOCUS_CHANGED, fileJList.getSelectedValue());
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
			App.getInputManager().addFiles(selectedFiles);
		});

		bottomPanel.add(pickAFileButton);
	}

	private void initConvertButton() {
		JButton convertButton = new JButton("Convert selected files");
		bottomPanel.add(convertButton);
	}
}






























