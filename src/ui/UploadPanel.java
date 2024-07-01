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

public class UploadPanel extends JPanel {
    UploadPanel() {
        initComponents();
        initBorder();
        initTransferHandling();
    }

    private void initTransferHandling() {
        initTransferHandler();
        initDropTarget();
    }

    private void initDropTarget() {
        this.setDropTarget(new DropTarget(this, new DropTarget(this, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                // Drop validation
                final TransferHandler.TransferSupport transferSupport = new TransferHandler.TransferSupport(UploadPanel.this, dtde.getTransferable());

                if (getTransferHandler().canImport(transferSupport))
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                else {
                    dtde.rejectDrop();
                    return;
                }

                // Import
                getTransferHandler().importData(UploadPanel.this, dtde.getTransferable());
            }
        })));
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
                    @SuppressWarnings("unchecked") var files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

                    HashMap<String, ImageReader> readers = new HashMap<>();

                    for (File file : files) {
                        var suffix = file.getName().split("\\.")[1];

                        if (!readers.containsKey(suffix)) {
                            readers.put(suffix, ImageIO.getImageReadersBySuffix(suffix).next());
                        }

                        var reader = readers.get(suffix);
                        reader.setInput(ImageIO.createImageInputStream(file));
                        var image = reader.read(0).getScaledInstance(400, 400, Image.SCALE_FAST);
                        JLabel label = new JLabel(new ImageIcon(image));
                        label.setBorder(BorderFactory.createDashedBorder(null));
                        UploadPanel.this.add(label, BorderLayout.CENTER);
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    return false;
                }

                return true;
            }
        });
    }

    private void initBorder() {
        final Border lineBorder = BorderFactory.createLineBorder(Color.RED);
        this.setBorder(lineBorder);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        final JLabel title = new JLabel("Upload Panel");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(title, BorderLayout.NORTH);
    }
}
