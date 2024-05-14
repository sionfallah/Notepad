import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;

public class ChooseFont extends JDialog implements ActionListener {
    private Font selectedFont;
    private JButton okButton;
    private JButton cancelButton;
    private JList<String> fList;
    private JSlider slider;
    private JRadioButton reg;
    private JRadioButton italic;
    private JRadioButton bold;

    public ChooseFont(JFrame parent, String title, Font initialFont) {
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
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
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

    @Override
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
}