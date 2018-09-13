/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.gui;

import java.awt.Component;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 *
 * @author PJ
 */
public class WidgetMaker {

    ResourceBundle rb;

    public WidgetMaker() {
        Locale compLocale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
        rb = ResourceBundle.getBundle("QMCLogicMinimizer/languages.Language", compLocale);
    }

    public JMenu newJMenu(JMenuBar menuBar, String text, boolean enable, boolean addToMenuBar) {
        JMenu njm = new JMenu(rb.getString(text), true);
        njm.setEnabled(enable);
        if (addToMenuBar) {
            menuBar.add(njm);
        }
        return njm;
    }

    public JMenuItem newJMenuItem(JMenu jmenu, String text, String icon, int key, boolean enable) {
        JMenuItem jmi;
        if (icon != null) {
            java.net.URL newIconURl = WidgetMaker.class.getResource("/QMCLogicMinimizer/icons/" + icon);
            Icon newIcon = new ImageIcon(newIconURl);
            jmi = new JMenuItem(rb.getString(text), newIcon);
        } else {
            jmi = new JMenuItem(rb.getString(text));
        }
        if (key != 0) {
            jmi.setAccelerator(KeyStroke.getKeyStroke(key, CTRL_DOWN_MASK));
        }
        jmi.setEnabled(enable);
        if (jmenu != null) {
            jmenu.add(jmi);
        }
        return jmi;
    }

    public JRadioButtonMenuItem newJRadioButtonMenuItem(String text, String icon, int key, boolean enable) {
        JRadioButtonMenuItem jrbmi;
        if (icon != null) {
            java.net.URL newIconURl = WidgetMaker.class.getResource("/QMCLogicMinimizer/icons/" + icon);
            Icon newIcon = new ImageIcon(newIconURl);
            jrbmi = new JRadioButtonMenuItem(rb.getString(text), newIcon);
        } else {
            jrbmi = new JRadioButtonMenuItem(rb.getString(text));
        }
        if (key != 0) {
            jrbmi.setAccelerator(KeyStroke.getKeyStroke(key, CTRL_DOWN_MASK));
        }
        jrbmi.setEnabled(enable);
        return jrbmi;
    }

    public JButton newJButton(String text, String toolTipText, String icon, boolean enable) {
        JButton newJButton;
        if (icon != null) {
            java.net.URL newIcon24URl = WidgetMaker.class.getResource("/QMCLogicMinimizer/icons/" + icon);
            Icon newIcon24 = new ImageIcon(newIcon24URl);
            newJButton = new JButton(newIcon24);
        } else {
            newJButton = new JButton(rb.getString(text));
        }
        newJButton.setToolTipText(rb.getString(toolTipText));
        newJButton.setEnabled(enable);
        return newJButton;
    }

    public JScrollPane newJScrollPane(Component comp) {
        JScrollPane nsp = new JScrollPane(comp);
        return nsp;
    }

    public JLabel newJLabel(String text) {
        JLabel njl = new JLabel(rb.getString(text));
        return njl;
    }

    public String getComponentText(String text) {
        return rb.getString(text);
    }

    public SpinnerModel newSpinnerNumberModel(int a, int b, int c, int d) {
        SpinnerModel sm = new SpinnerNumberModel(a, b, c, d);
        return sm;
    }

    public JSpinner newJSpinner(SpinnerModel sm) {
        JSpinner nsp = new JSpinner(sm);
        return nsp;
    }

    public JPopupMenu newJPopupMenu() {
        JPopupMenu npm = new JPopupMenu();
        return npm;
    }
}
