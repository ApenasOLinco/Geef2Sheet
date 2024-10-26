package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.naming.CompositeName;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import main.App;

public class OptionsPanel extends JPanel {
	JPanel contentPanel = new JPanel();
	
	JSpinner numberOfColumnsSpinner = new JSpinner(), horizontalGapSpinner = new JSpinner(),
	verticalGapSpinner = new JSpinner();
	
	JComboBox<String> outputFormatJComboBox = new JComboBox<>();
	
	JScrollPane scrollPane = new JScrollPane();
	
	public OptionsPanel() {
		setLayout(new BorderLayout());
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		initNumberOfColumnsSpinner();
		initGapSpinners();
		
		scrollPane.setViewportView(contentPanel);
		add(scrollPane);
	}
	
	private void initNumberOfColumnsSpinner() {
		SpinnerNumberModel columnsModel = new SpinnerNumberModel();
		columnsModel.setMinimum(1);
		numberOfColumnsSpinner.setModel(columnsModel);
		contentPanel.add(numberOfColumnsSpinner);
	}
	
	private void initGapSpinners() {
		// Horizontal Spinner
		createSpinner(horizontalGapSpinner, "Horizontal Gap:");
		
		// Vertical Spinner
		createSpinner(verticalGapSpinner, "Vertical Gap:");
	}
	
	private void createSpinner(JSpinner spinner, String labelText) {
		Dimension spinnerMinimumSize = new Dimension(50, 20);
		Dimension spinnerMaximumSize = new Dimension(500, 20);
		
		SpinnerNumberModel gapModel = new SpinnerNumberModel();
		gapModel.setMinimum(0);
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.LINE_AXIS));
		
		spinner.setModel(gapModel);
		spinner.setMinimumSize(spinnerMinimumSize);
		spinner.setMaximumSize(spinnerMaximumSize);
		spinner.setPreferredSize(spinnerMaximumSize);
		
		JLabel label = new JLabel(labelText);
		
		container.add(label);
		container.add(Box.createHorizontalGlue());
		container.add(spinner);
		contentPanel.add(container);
	}
}
