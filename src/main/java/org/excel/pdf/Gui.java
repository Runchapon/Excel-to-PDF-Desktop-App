package org.excel.pdf;

import org.excel.pdf.excel.ExcelReader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;
import java.util.List;

public class Gui extends JFrame {

    public Gui() {
        super("Convert Excel to PDF");
        initialGui();
    }

    public void initialGui(){

        setUI();
        // Create the basic frame
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a central panel
        JPanel central = new JPanel(new BorderLayout());
        central.setBackground(Color.LIGHT_GRAY);
        super.getContentPane().add(central, BorderLayout.CENTER);

        JLabel dropLabel = getDropLabel();

        super.getContentPane().add(dropLabel);

        // add select file button
//        JButton jButton = new JButton("Select File");
//        jButton.addActionListener(Button.selectFileButton(jButton));
//
//        frame.add(jButton);

        // Show the Frame
        super.pack();
        super.setSize(new Dimension(1500, 1500));
        super.setVisible(true);
    }

    private JLabel getDropLabel() {
        JLabel dropLabel = dropLabel();

        // Create the drag and drop listener
        FileDropListener myDragDropListener = new FileDropListener(this, dropLabel);

        // Connect the label with a drag and drop listener
        new DropTarget(dropLabel, myDragDropListener);
        return dropLabel;
    }

    private JLabel dropLabel() {
        MouseListener focusHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Component c = ((JLabel) e.getComponent()).getLabelFor();
                if (c != null) {
                    c.requestFocusInWindow();
                }
            }
        };

        // Create the label
        JLabel dropLabel = new JLabel("Drag and Drop Excel File here", SwingConstants.CENTER);
        dropLabel.addMouseListener(focusHandler);
        return dropLabel;
    }

    private void setUI() {
        try {
            // see nice icons in chooser!
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception useDefault) {
            System.out.println("UIManager failed");
        }
    }

    private static class Button {
        public static ActionListener selectFileButton(JButton jButton) {
            return e -> {
                FileFilter filter = new FileNameExtensionFilter("xlsx", "txt", "csv");
                //Create a file chooser
                final JFileChooser fc = new JFileChooser();
                fc.setFont(new Font("AngsanaUPC", Font.PLAIN, 10));
                fc.setLocale(new Locale("th","TH","TH"));
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.showDialog(null, "Choose file");
                fc.addChoosableFileFilter(filter);

                //In response to a button click:
                int returnVal = fc.showOpenDialog(jButton);

                if (JFileChooser.APPROVE_OPTION == returnVal) {
                    File selectedFile = fc.getSelectedFile();
                    System.out.println(selectedFile.getName());
                }
            };
        }
    }

    private static class FileDropListener implements DropTargetListener {

        private final JFrame jFrame;
        private JLabel dropLabel;

        public FileDropListener(JFrame jFrame, JLabel dropLabel) {
            this.jFrame = jFrame;
            this.dropLabel = dropLabel;
        }

        @Override
        public void drop(DropTargetDropEvent event) {

            // Accept copy drops
            event.acceptDrop(DnDConstants.ACTION_COPY);

            // Get the transfer which can provide the dropped item data
            Transferable transferable = event.getTransferable();

            // Get the data formats of the dropped item
            DataFlavor[] flavors = transferable.getTransferDataFlavors();

            // Loop through the flavors
            for (DataFlavor flavor : flavors) {

                try {

                    // If the drop items are files
                    if (flavor.isFlavorJavaFileListType()) {

                        // Get all of the dropped files
                        List<File> files = (List) transferable.getTransferData(flavor);

                        // Loop them through
                        for (File file : files) {

                            if (!file.isFile()) {
                                // TODO - add alert
                                System.out.println("This is not file");
                            } else if (!file.getName().endsWith(".xlsx")) {
                                // TODO - add alert
                                System.out.println("Not an excel file");
                            } else {
                                // Print out the file path
                                System.out.println("File path is '" + file.getPath() + "'.");
                                ExcelReader reader = new ExcelReader();
//                                reader.readExcel(file.getAbsolutePath());
                            }

                        }

                    }

                } catch (Exception e) {

                    // Print out the error stack
                    e.printStackTrace();

                }
            }

            // Inform that the drop is complete
            event.dropComplete(true);

        }

        @Override
        public void dragEnter(DropTargetDragEvent event) {
        }

        @Override
        public void dragExit(DropTargetEvent event) {
        }

        @Override
        public void dragOver(DropTargetDragEvent event) {
            event.acceptDrag(DnDConstants.ACTION_MOVE);

            if (event.getDropAction() == DnDConstants.ACTION_MOVE) {
                this.dropLabel.setBackground(Color.GREEN);
            }
        }

        @Override
        public void dropActionChanged(DropTargetDragEvent event) {
        }

        public JFrame getjFrame() {
            return jFrame;
        }

        public JLabel getDropLabel() {
            return dropLabel;
        }

        public void setDropLabel(JLabel dropLabel) {
            this.dropLabel = dropLabel;
        }
    }
}
