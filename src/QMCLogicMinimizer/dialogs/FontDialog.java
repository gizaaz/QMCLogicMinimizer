/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.dialogs;

import QMCLogicMinimizer.gui.WidgetMaker;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author OK3PJ
 */
public class FontDialog extends JDialog {

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootpane = super.createRootPane();
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        rootpane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // The action to be executed when Escape is pressed  
                cancel();
            }
        }, escape, JComponent.WHEN_IN_FOCUSED_WINDOW);
        rootpane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        }, enter, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootpane;
    }
    private JList fontList;
    private JList styleList;
    private JList sizeList;
    private JList colorList;
    private JLabel sampleLabel;
    private Font newFont = null;
    private Color newColor;

    public FontDialog(JFrame owner, WidgetMaker wmk, Font actualFont, Color actualTxtColor, int maxTextSize) {
        super(owner, wmk.getComponentText("fdSuperText"), true);
        ///////////////////////////////
        // layout manager
        ///////////////////////////////
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        this.setLocation(200, 200);
        this.setSize(600, 400);
        ///////////////////////////////
        // label Font
        ///////////////////////////////
        JLabel fontLabel = wmk.newJLabel("fdLabelFont");
        gbc.gridx = 0;
        gbc.gridy = 0;
        fontLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbl.setConstraints(fontLabel, gbc);
        this.add(fontLabel);
        ///////////////////////////////
        // label Font style
        ///////////////////////////////
        JLabel styleLabel = wmk.newJLabel("fdLabelStyle");
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        styleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbl.setConstraints(styleLabel, gbc);
        this.add(styleLabel);
        ///////////////////////////////
        // label Size
        ///////////////////////////////
        JLabel sizeLabel = wmk.newJLabel("fdLabelSize");
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        sizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbl.setConstraints(sizeLabel, gbc);
        this.add(sizeLabel);
        ///////////////////////////////
        // label Color
        ///////////////////////////////
        JLabel colorLabel = wmk.newJLabel("fdLabelColor");
        gbc.gridx = 3;
        gbc.weightx = 0.3;
        colorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbl.setConstraints(colorLabel, gbc);
        this.add(colorLabel);
        ///////////////////////////////
        // Lists Selection Listener
        ///////////////////////////////
        ListSelectionListener listSelListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                makeFont();
            }
        };
        ///////////////////////////////
        // list Font
        ///////////////////////////////
        gbc.weighty = 1.0;
        fontList = new JList();
        JScrollPane fontListScPn = new JScrollPane(fontList);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbl.setConstraints(fontListScPn, gbc);
        this.add(fontListScPn);
        // nacteni vsech dostupnych fontu v systemu
        GraphicsEnvironment sysFonts = GraphicsEnvironment.getLocalGraphicsEnvironment();
        DefaultListModel fonts = new DefaultListModel();
        String[] strFonts = sysFonts.getAvailableFontFamilyNames();
        for (int i = 0; i < strFonts.length; i++) {
            fonts.addElement(strFonts[i]);
        }
        fontList.setModel(fonts);
        fontList.addListSelectionListener(listSelListener);
        ///////////////////////////////
        // list  style
        ///////////////////////////////
        styleList = new JList();
        JScrollPane styleListScPn = new JScrollPane(styleList);
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbl.setConstraints(styleListScPn, gbc);
        this.add(styleListScPn);
        DefaultListModel styles = new DefaultListModel();
        styles.addElement("Plain");
        styles.addElement("Bold");
        styles.addElement("Italic");
        styleList.setModel(styles);
        styleList.addListSelectionListener(listSelListener);
        ///////////////////////////////
        // list Size
        ///////////////////////////////
        sizeList = new JList();
        JScrollPane sizeListScPn = new JScrollPane(sizeList);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbl.setConstraints(sizeListScPn, gbc);
        this.add(sizeListScPn);
        DefaultListModel sizes = new DefaultListModel();
        for (int j = 6; j <= maxTextSize; j++) {
            sizes.addElement(j);
        }
        sizeList.setModel(sizes);
        sizeList.addListSelectionListener(listSelListener);
        ///////////////////////////////
        // list Color
        ///////////////////////////////
        colorList = new JList();
        JScrollPane colorListScPn = new JScrollPane(colorList);
        gbc.gridx = 3;
        gbc.weightx = 0.3;
        gbl.setConstraints(colorListScPn, gbc);
        this.add(colorListScPn);
        DefaultListModel colors = new DefaultListModel();
        colors.addElement(wmk.getComponentText("fdColorBlack"));
        colors.addElement(wmk.getComponentText("fdColorWhite"));
        colors.addElement(wmk.getComponentText("fdColorRed"));
        colors.addElement(wmk.getComponentText("fdColorGreen"));
        colors.addElement(wmk.getComponentText("fdColorBlue"));
        colors.addElement(wmk.getComponentText("fdColorYellow"));
        colors.addElement(wmk.getComponentText("fdColorCyan"));
        colors.addElement(wmk.getComponentText("fdColorMagenta"));
        colors.addElement(wmk.getComponentText("fdColorOrange"));
        colors.addElement(wmk.getComponentText("fdColorPink"));
        colors.addElement(wmk.getComponentText("fdColorLightGray"));
        colors.addElement(wmk.getComponentText("fdColorGray"));
        colors.addElement(wmk.getComponentText("fdColorDarkGray"));
        colorList.setModel(colors);
        colorList.addListSelectionListener(listSelListener);
        ///////////////////////////////
        // label Sample
        ///////////////////////////////
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.gridwidth = 2;
        sampleLabel = wmk.newJLabel("fdLabelSampleText");
        sampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbl.setConstraints(sampleLabel, gbc);
        this.add(sampleLabel);
        sampleLabel.setFont(actualFont);
        sampleLabel.setForeground(actualTxtColor);
        ///////////////////////////////
        // ok Button
        ///////////////////////////////
        gbc.weightx = 0.2;
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        JButton okButton = wmk.newJButton("fdOkButton", "fdOkButton", null, true);
        gbl.setConstraints(okButton, gbc);
        this.add(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
        ///////////////////////////////
        // cancel Button
        ///////////////////////////////
        gbc.weightx = 0.3;
        gbc.gridx = 3;
        JButton cancelButton = wmk.newJButton("fdCancelButton", "fdCancelButton", null, true);
        gbl.setConstraints(cancelButton, gbc);
        this.add(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        ///////////////////////////////
        // nacteni aktualniho fontu
        ///////////////////////////////
        for (int k = 0; k < fontList.getModel().getSize(); k++) {
            if (actualFont.getFamily().equals(fontList.getModel().getElementAt(k).toString())) {
                fontList.setSelectedIndex(k);
            }
        }
        styleList.setSelectedIndex(actualFont.getStyle());
        sizeList.setSelectedIndex(actualFont.getSize() - 6);
        switch (actualTxtColor.hashCode()) {
            case -16777216:
                colorList.setSelectedIndex(0);
                break;
            case -1:
                colorList.setSelectedIndex(1);
                break;
            case -65536:
                colorList.setSelectedIndex(2);
                break;
            case -16711936:
                colorList.setSelectedIndex(3);
                break;
            case -16776961:
                colorList.setSelectedIndex(4);
                break;
            case -256:
                colorList.setSelectedIndex(5);
                break;
            case -16711681:
                colorList.setSelectedIndex(6);
                break;
            case -65281:
                colorList.setSelectedIndex(7);
                break;
            case -14336:
                colorList.setSelectedIndex(8);
                break;
            case -20561:
                colorList.setSelectedIndex(9);
                break;
            case -4144960:
                colorList.setSelectedIndex(10);
                break;
            case -8355712:
                colorList.setSelectedIndex(11);
                break;
            case -12566464:
                colorList.setSelectedIndex(12);
                break;
        }
    }

    private void makeFont() {
        newFont = new Font(fontList.getSelectedValue().toString(), styleList.getSelectedIndex(), sizeList.getSelectedIndex() + 6);
        sampleLabel.setFont(newFont);
        newColor = null;
        switch (colorList.getSelectedIndex()) {
            case 0:
                newColor = Color.black;
                break;
            case 1:
                newColor = Color.white;
                break;
            case 2:
                newColor = Color.red;
                break;
            case 3:
                newColor = Color.green;
                break;
            case 4:
                newColor = Color.blue;
                break;
            case 5:
                newColor = Color.yellow;
                break;
            case 6:
                newColor = Color.cyan;
                break;
            case 7:
                newColor = Color.magenta;
                break;
            case 8:
                newColor = Color.orange;
                break;
            case 9:
                newColor = Color.pink;
                break;
            case 10:
                newColor = Color.lightGray;
                break;
            case 11:
                newColor = Color.gray;
                break;
            case 12:
                newColor = Color.darkGray;
                break;
        }
        sampleLabel.setForeground(newColor);
    }

    private void ok() {
        this.dispose();
    }

    private void cancel() {
        newFont = null;
        this.dispose();
    }

    public Font getNewFont() {
        return newFont;
    }

    public Color getNewColor() {
        return newColor;
    }
}
