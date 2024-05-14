import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class Notepad {
    private JTextArea textArea;
    private JLabel filenameLabel;
    //Easy EC, replace, view Help, Extra Credits

    public Notepad() {
        //Set up Frame
        JFrame frame = new JFrame("Notepad");
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setIconImage(new ImageIcon("Notepad.png").getImage());

        // Create the text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the text area to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        //MenuBar
        JMenuBar jmb = new JMenuBar();

        //Create File Tab with all items
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        JMenuItem open = new JMenuItem("Open");
        open.setMnemonic('O');
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem save = new JMenuItem("Save");
        save.setMnemonic('S');
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem saveAs = new JMenuItem("Save As");
        saveAs.setMnemonic(2);
        JMenuItem pSetUp = new JMenuItem("Page Setup");
        pSetUp.setMnemonic('U');
        pSetUp.setEnabled(false);
        JMenuItem print = new JMenuItem("Print");
        print.setMnemonic('P');
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        print.setEnabled(false);
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic('X');
        
        
        file.add(newItem);
        file.add(open);
        file.add(save);
        file.add(saveAs);
        file.addSeparator();
        file.add(pSetUp);
        file.add(print);
        file.addSeparator();
        file.add(exit);

        //Create Edit Tab with all items
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E');
        JMenuItem undo = new JMenuItem("Undo");
        undo.setMnemonic('U');
        undo.setEnabled(false);
        JMenuItem cut = new JMenuItem("Cut");
        cut.setMnemonic('t');
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        JMenuItem copy = new JMenuItem("Copy");
        copy.setMnemonic('C');
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        JMenuItem paste = new JMenuItem("Paste");
        paste.setMnemonic('P');
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        JMenuItem delete = new JMenuItem("Delete");
        delete.setMnemonic('l');
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        JMenuItem find = new JMenuItem("Find");
        find.setMnemonic('F');
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        JMenuItem fNext = new JMenuItem("Find Next");
        fNext.setMnemonic('N');
        fNext.setEnabled(false);
        JMenuItem replace = new JMenuItem("Replace");
        replace.setMnemonic('R');
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        replace.setEnabled(false);
        JMenuItem goTo = new JMenuItem("Go To");
        goTo.setMnemonic('G');
        goTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setMnemonic('A');
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        JMenuItem td = new JMenuItem("Time/Date");
        td.setMnemonic('D');
        td.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,0));

        edit.add(undo);
        edit.addSeparator();
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);
        edit.addSeparator();
        edit.add(find);
        edit.add(fNext);
        edit.add(replace);
        edit.add(goTo);
        edit.addSeparator();
        edit.add(selectAll);
        edit.add(td);

        //Create Format Tab with all items
        JMenu format = new JMenu("Format");
        format.setMnemonic('O');
        JMenuItem wordWrap = new JMenuItem("Word Wrap");
        wordWrap.setMnemonic('W');
        JMenuItem font = new JMenuItem("Font");
        font.setMnemonic('F');
        JMenu color = new JMenu("Color");
        color.setMnemonic('C');
        JMenuItem background = new JMenuItem("Background");
        background.setMnemonic('B');
        JMenuItem foreground = new JMenuItem("Foreground");
        foreground.setMnemonic('F');
        color.add(background);
        color.add(foreground);

        format.add(wordWrap);
        format.add(font);
        format.add(color);

        //Create View Tab with all items
        JMenu view = new JMenu("View");
        view.setMnemonic('V');
        JMenuItem status = new JMenuItem("Status Bar");
        status.setMnemonic('S');
        status.setEnabled(false);

        view.add(status);

        //Create Help Tab with all items
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        JMenuItem vHelp = new JMenuItem("View Help");
        vHelp.setMnemonic('H');
        vHelp.setEnabled(false);
        JMenuItem eCredit = new JMenuItem("Extra Credits");
        eCredit.setMnemonic('x');
        eCredit.setEnabled(false);
        JMenuItem abt = new JMenuItem("About Notepad");
        abt.setMnemonic('A');
        

        help.add(vHelp);
        help.add(eCredit);
        help.addSeparator();
        help.add(abt);

        //Add Every tab to Menu Bar
        jmb.add(file);
        jmb.add(edit);
        jmb.add(format);
        jmb.add(view);
        jmb.add(help);
        frame.setJMenuBar(jmb);

        saveAs.addActionListener(e -> {
            Dialogs.saveAsDialog(frame, textArea);
        });

        newItem.addActionListener(e -> {
            Dialogs.newFileDialog(frame, textArea);
        });

        //open
        filenameLabel = new JLabel("untitled.txt");
        frame.add(filenameLabel, BorderLayout.NORTH);
        open.addActionListener(e -> {
            Dialogs.openDialog(frame, "Open", textArea, filenameLabel);
        });
        
        save.addActionListener(e -> {
            Dialogs.saveDialog(frame, "Save", textArea);
        });
        
        exit.addActionListener(e -> {
            if (Dialogs.exitDialog(frame, textArea)) {
                System.exit(0);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showPopupMenu(e);
                }
            }
        });

        cut.addActionListener(e -> {
            textArea.cut();
        });

        copy.addActionListener(e -> {
            textArea.copy();
        });

        paste.addActionListener(e -> {
            textArea.paste();
        });

        delete.addActionListener(e -> {
            textArea.replaceSelection("");
        });

        find.addActionListener(e -> {
            Dialogs.findDialog(frame, textArea);
        });

        goTo.addActionListener(e -> Dialogs.goToDialog(frame, textArea));

        selectAll.addActionListener(e -> {
            textArea.selectAll();
        });

        td.addActionListener(e -> {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a MM/dd/yyyy");
            Date date = new Date();
            String dt = dateFormat.format(date);
            textArea.insert(dt, textArea.getCaretPosition());
        });

        textArea.setLineWrap(false);

        wordWrap.addActionListener(e -> {
            boolean isLineWrapEnabled = textArea.getLineWrap();
            textArea.setLineWrap(!isLineWrapEnabled); // Toggle word wrap
        });

        font.addActionListener(e -> {
            Font initialFont = textArea.getFont(); 
            Font selectedFont = Dialogs.showFontDialog(frame, "Select Font", initialFont);
            if (selectedFont != null) {
                textArea.setFont(selectedFont);
            }
        });

        background.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Background Color", textArea.getBackground());
            if (selectedColor != null) {
                textArea.setBackground(selectedColor);
            }
        });
        
        foreground.addActionListener(e -> {
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Foreground Color", textArea.getForeground());
            if (selectedColor != null) {
                textArea.setForeground(selectedColor);
            }
        });

        abt.addActionListener(e -> Dialogs.showAboutDialog());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void showPopupMenu(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.setMnemonic('t');
        cutItem.addActionListener(ev -> textArea.cut());

        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.setMnemonic('C');
        copyItem.addActionListener(ev -> textArea.copy());

        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.setMnemonic('P');
        pasteItem.addActionListener(ev -> textArea.paste());

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.setMnemonic('l');
        deleteItem.addActionListener(ev -> textArea.replaceSelection(""));

        popupMenu.add(cutItem);
        popupMenu.add(copyItem);
        popupMenu.add(pasteItem);
        popupMenu.add(deleteItem);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> new Notepad());
    }
}
