package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;

import main.App;

public class UploadPanel extends JPanel {
	
	private UploadLeftPanel leftPanel;
	private JPanel displayPanel;
	private JSplitPane splitPane;
	
	UploadPanel() {
		initComponents();
		initTransferHandling();
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		initSplitPane();
		initTitleLabel();
		initLeftPanel();
		initDisplayPanel();
	}
	
	private void initTransferHandling() {
		initDropTarget();
		initTransferHandler();
	}
	
	private void initDropTarget() {
		this.setDropTarget(new DropTarget(this, new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde) {
				// Drop validation
				final TransferHandler.TransferSupport transferSupport = new TransferHandler.TransferSupport(
				UploadPanel.this, dtde.getTransferable());
				
				if (getTransferHandler().canImport(transferSupport))
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
				else {
					dtde.rejectDrop();
					return;
				}
				
				// Import
				getTransferHandler().importData(UploadPanel.this, dtde.getTransferable());
			}
		}));
	}
	
	private void initTransferHandler() {
		this.setTransferHandler(new TransferHandler(null) {
			@Override
			public boolean canImport(TransferSupport support) {
				return Arrays.stream(support.getDataFlavors()).anyMatch(DataFlavor::isFlavorJavaFileListType);
			}
			
			@Override
			public boolean importData(JComponent comp, Transferable t) {
				try {
					@SuppressWarnings("unchecked")
					var files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
					final boolean wasAdded = App.getInputProcessor().addFiles(files.toArray(new File[0]));
					if (!wasAdded)
						return false;
				} catch (UnsupportedFlavorException e) {
					System.err.printf("""
					Tried getting transfer data of an invalid Flavor while importing %s.
					   Tried flavor: %s
					   Supported flavors: %s
					""", t, DataFlavor.javaFileListFlavor, Arrays.toString(t.getTransferDataFlavors()));
					
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				
				return true;
			}
		});
	}
	
	private void initTitleLabel() {
		final JLabel title = new JLabel("Upload Panel");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setOpaque(true);
		title.setBackground(Color.LIGHT_GRAY);
		this.add(title, BorderLayout.NORTH);
	}
	
	private void initLeftPanel() {
		leftPanel = new UploadLeftPanel(this);
		splitPane.setLeftComponent(leftPanel);
	}
	
	private void initDisplayPanel() {
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.add(new ScalableLabel(null));
		splitPane.setRightComponent(displayPanel);
	}
	
	private void initSplitPane() {
		splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		add(splitPane);
	}
	
	public void updateDisplayPanel(ImageIcon image) {
		ScalableLabel label = (ScalableLabel) displayPanel.getComponent(0);
		
		label.setIcon(image);
		App.getAppWindow().queueRepaint(label);
		label.revalidate();
	}
}




















