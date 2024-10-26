package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import io.OutputConfigurations;

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
		
		initSpinners();
		
		scrollPane.setViewportView(contentPanel);
		add(scrollPane);
	}
	
	private void initSpinners() {
		// Number Of Columns Spinner
		createSpinner(numberOfColumnsSpinner, "Number of Columns: ");
		numberOfColumnsSpinner.setValue(OutputConfigurations.getNumberOfColumns());

		// Horizontal Spinner
		createSpinner(horizontalGapSpinner, "Horizontal Gap: ");
		horizontalGapSpinner.setValue(OutputConfigurations.gethGap());
		
		// Vertical Spinner
		createSpinner(verticalGapSpinner, "Vertical Gap: ");
		verticalGapSpinner.setValue(OutputConfigurations.getvGap());
	}
	
	private void createSpinner(JSpinner spinner, String labelText) {
		Dimension spinnerMinimumSize = new Dimension(25, 25);
		Dimension spinnerMaximumSize = new Dimension(500, 25);
		
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(0);
		
		// Container
		JPanel container = new JPanel();
		BoxLayout layout = new BoxLayout(container, BoxLayout.LINE_AXIS);
		container.setLayout(layout);
		container.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		spinner.setModel(model);
		spinner.setMinimumSize(spinnerMinimumSize);
		spinner.setMaximumSize(spinnerMaximumSize);
		spinner.setPreferredSize(spinnerMaximumSize);
		
		// Centralize the text on the spinner
		JComponent editor = spinner.getEditor();
		
		if(editor instanceof JSpinner.DefaultEditor) {
			var defaultEditor = (DefaultEditor) editor;
			JFormattedTextField textField = defaultEditor.getTextField();
			textField.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		JLabel label = new JLabel(labelText);
		
		container.add(label);
		container.add(Box.createHorizontalGlue());
		container.add(spinner);
		contentPanel.add(container);
	}
}
