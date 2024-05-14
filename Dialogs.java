import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

public class Dialogs extends JDialog{
    //newFile
    public static void newFileDialog(JFrame parent, JTextArea textArea) {
        int option = JOptionPane.showConfirmDialog(parent, "Do you want to save changes to Untitled?", "Notepad", JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            saveDialog(parent, "Save As", textArea);
            clearTextArea(textArea);
        } else if (option == JOptionPane.NO_OPTION) {
            clearTextArea(textArea);
        }
    }

    private static void clearTextArea(JTextArea textArea) {
        textArea.setText("");
    }

    //save
    public static void saveDialog(JFrame parent, String title, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt, .java)", "txt", "java");
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //Open
    public static void openDialog(JFrame parent, String title, JTextArea textArea, JLabel filenameLabel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt, .java)", "txt", "java");
        fileChooser.setFileFilter(filter);
    
        int option = fileChooser.showOpenDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                filenameLabel.setText(selectedFile.getName()); // Display filename on the label
                try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    textArea.setText(content.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    public static void saveAsDialog(JFrame parent, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showSaveDialog(parent);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean exitDialog(JFrame parent, JTextArea textArea) {
        int option = JOptionPane.showConfirmDialog(parent, "Do you want to save changes before exiting?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            saveDialog(parent, "Save", textArea);
            return true;
        } else if (option == JOptionPane.NO_OPTION) {
            return true;
        }
        return false;
    }

    public static void goToDialog(JFrame parent, JTextArea textArea) {
        JDialog goToDialog = new JDialog(parent, "Go To", false);
        goToDialog.setSize(300, 150);
        goToDialog.setResizable(false);
        goToDialog.setLayout(new BorderLayout());
        goToDialog.setLocationRelativeTo(parent);

        JLabel lineLabel = new JLabel("Line Number:");
        JTextField lineTextField = new JTextField(10);
        JButton goToButton = new JButton("Go To");
        JButton cancelButton = new JButton("Cancel");

        goToButton.addActionListener(e -> {
            try {
                int lineNumber = Integer.parseInt(lineTextField.getText());
                int lineCount = textArea.getLineCount();
                if (lineNumber >= 1 && lineNumber <= lineCount) {
                    int startIndex = textArea.getLineStartOffset(lineNumber - 1);
                    textArea.setCaretPosition(startIndex);
                    textArea.requestFocusInWindow();
                    goToDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(goToDialog, "Invalid line number", "Go To", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException | BadLocationException ex) {
                JOptionPane.showMessageDialog(goToDialog, "Invalid line number", "Go To", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> goToDialog.dispose());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(lineLabel);
        panel.add(lineTextField);
        panel.add(goToButton);
        panel.add(cancelButton);
        goToDialog.add(panel, BorderLayout.CENTER);

        goToDialog.setVisible(true);
    }

    private static int lastFoundIndex = -1;

    public static void findDialog(JFrame parent, JTextArea textArea) {
        JDialog findDialog = new JDialog(parent, "Find", false);
        findDialog.setSize(300, 150);
        findDialog.setResizable(false);
        findDialog.setLayout(new BorderLayout());
        findDialog.setLocationRelativeTo(parent);

        JLabel findLabel = new JLabel("Find:");
        JTextField findTextField = new JTextField(20);
        JButton findButton = new JButton("Find Next");
        JButton cancelButton = new JButton("Cancel");

        findButton.addActionListener(e -> {
            String searchText = findTextField.getText();
            if (searchText != null && !searchText.isEmpty()) {
                String text = textArea.getText();
                int index = text.indexOf(searchText, lastFoundIndex + 1); //So it actually goes to next one
                if (index != -1) {
                    textArea.setSelectionStart(index);
                    textArea.setSelectionEnd(index + searchText.length());
                    lastFoundIndex = index; // Saves the last one
                } else {
                    JOptionPane.showMessageDialog(findDialog, "No more occurrences found", "Find", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            findDialog.dispose();
            lastFoundIndex = -1; //Gets rid of last saved index
        });

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(findLabel);
        panel.add(findTextField);
        panel.add(findButton);
        panel.add(cancelButton);
        findDialog.add(panel, BorderLayout.CENTER);

        findDialog.setVisible(true);
    }

    private Font selectedFont;
    private JButton okButton;
    private JButton cancelButton;
    private JList<String> fList;
    private JSlider slider;
    private JRadioButton reg;
    private JRadioButton italic;
    private JRadioButton bold;

    public Dialogs(JFrame parent, String title, Font initialFont) {
        super(parent, title, true);
        selectedFont = initialFont;

        JPanel panel = new JPanel(new BorderLayout());

        // Slider
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        JLabel sizeText = new JLabel("Size:");
        sizeText.setDisplayedMnemonic('S');
        north.add(sizeText, BorderLayout.NORTH);
        slider = new JSlider(8, 20); // Adjust range as needed
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(1);
        north.add(slider, BorderLayout.CENTER);
        panel.add(north, BorderLayout.NORTH);

        // Fonts List
        JPanel west = new JPanel(new BorderLayout());
        JLabel fnts = new JLabel("Fonts:");
        fList = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fList.setSelectedValue(selectedFont.getFontName(), true);
        JScrollPane jscrlp = new JScrollPane(fList);
        west.add(fnts, BorderLayout.NORTH);
        west.add(jscrlp, BorderLayout.CENTER);

        // Style and Size
        JPanel center = new JPanel(new GridLayout(4, 1));
        JLabel style = new JLabel("Style:");
        ButtonGroup styleGroup = new ButtonGroup();
        reg = new JRadioButton("Regular");
        italic = new JRadioButton("Italic");
        bold = new JRadioButton("Bold");
        styleGroup.add(reg);
        styleGroup.add(italic);
        styleGroup.add(bold);
        reg.setMnemonic('R');
        italic.setMnemonic('I');
        bold.setMnemonic('B');
        center.add(style);
        center.add(reg);
        center.add(italic);
        center.add(bold);

        // Ok and Cancel Buttons
        JPanel south = new JPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        okButton.addActionListener((ActionListener) this);
        cancelButton.addActionListener((ActionListener) this);
        south.add(okButton);
        south.add(cancelButton);

        panel.add(west, BorderLayout.WEST);
        panel.add(center, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);

        add(panel);
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            selectedFont = new Font(fList.getSelectedValue(), getFontStyle(), slider.getValue());
        } else if (e.getSource() == cancelButton) {
            selectedFont = null;
        }
        dispose();
    }

    public Font getSelectedFont() {
        return selectedFont;
    }

    private int getFontStyle() {
        int style = Font.PLAIN;
        if (reg.isSelected()) {
            style = Font.PLAIN;
        } else if (italic.isSelected()) {
            style = Font.ITALIC;
        } else if (bold.isSelected()) {
            style = Font.BOLD;
        }
        return style;
    }
    

    public static Font showFontDialog(JFrame parent, String title, Font initialFont) {
        ChooseFont dialog = new ChooseFont(parent, title, initialFont);
        dialog.setVisible(true);
        return dialog.getSelectedFont();
    }

    public static void showAboutDialog() {
        JOptionPane.showMessageDialog(null,
            "Created by: Sion Fallah",
            "About Notepad",
            JOptionPane.INFORMATION_MESSAGE);
    }
}