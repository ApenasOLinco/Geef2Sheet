package ui;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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
import java.util.HashMap;
import java.util.List;

public class MainPanel extends JPanel {
    private final int width = 800, height = 600;
    private final JTabbedPane tabs = new JTabbedPane();
    private JPanel uploadPanel, optionsPanel;

    public MainPanel() {
        initSizes();
        initPanels();
        setLayout(new BorderLayout());
        add(tabs);
        tabs.setTabPlacement(JTabbedPane.BOTTOM);
        tabs.addTab("Upload", uploadPanel);
        tabs.addTab("Options", optionsPanel);
    }

    private void initPanels() {
        initUploadPanel();
        initOptionsPanel();
    }

    private void initUploadPanel() {
        uploadPanel = new JPanel();
        uploadPanel.setLayout(new BorderLayout());
        final JLabel title = new JLabel("Upload Panel");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        uploadPanel.add(title, BorderLayout.NORTH);

        // Border
        final Border lineBorder = BorderFactory.createLineBorder(Color.RED);
        uploadPanel.setBorder(lineBorder);

        // Transfer Handling
        uploadPanel.setTransferHandler(new TransferHandler(null) {
            @Override
            public boolean canImport(TransferSupport support) {
                return Arrays.stream(support.getDataFlavors()).anyMatch(DataFlavor::isFlavorJavaFileListType);
            }

            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    @SuppressWarnings("unchecked") var files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

                    HashMap<String, ImageReader> readers = new HashMap<>();

                    for(File file : files) {
                        var suffix = file.getName().split("\\.")[1];

                        if(!readers.containsKey(suffix)) {
                            readers.put(suffix, ImageIO.getImageReadersBySuffix(suffix).next());
                        }

                        var reader = readers.get(suffix);
                        reader.setInput(ImageIO.createImageInputStream(file));
                        var image = reader.read(0).getScaledInstance(400, 400, Image.SCALE_FAST);
                        JLabel label = new JLabel(new ImageIcon(image));
                        label.setBorder(BorderFactory.createDashedBorder(null));
                        uploadPanel.add(label, BorderLayout.CENTER);
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
        uploadPanel.setDropTarget(new DropTarget(uploadPanel, new DropTarget(uploadPanel, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                // Drop validation
                final TransferHandler.TransferSupport transferSupport = new TransferHandler.TransferSupport(uploadPanel, dtde.getTransferable());

                if(uploadPanel.getTransferHandler().canImport(transferSupport))
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                else {
                    dtde.rejectDrop();
                    return;
                }

                // Import
                uploadPanel.getTransferHandler().importData(uploadPanel, dtde.getTransferable());
            }
        })));
    }

    private void initOptionsPanel() {
        optionsPanel = new JPanel();
        optionsPanel.setLayout(getLayout());
        optionsPanel.add(new JLabel("Options Panel"), BorderLayout.NORTH);
    }

    private void initSizes() {
        Dimension size = new Dimension(width, height);
        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
    }
}
