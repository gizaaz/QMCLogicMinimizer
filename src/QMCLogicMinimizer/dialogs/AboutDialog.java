/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.dialogs;

import QMCLogicMinimizer.gui.WidgetMaker;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author OK3PJ
 */
public class AboutDialog extends JDialog {

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootpane = super.createRootPane();
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootpane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        }, escape, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootpane;
    }

    public AboutDialog(JFrame owner, WidgetMaker wmk) {
        super(owner, wmk.getComponentText("abdSuperText"), true);
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        this.setLocation(200, 200);
        this.setSize(500, 220);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        this.setResizable(false);
        java.net.URL abotIconURl = AboutDialog.class.getResource("/QMCLogicMinimizer/icons/qmclm.gif");
        Icon aboutIcon = new ImageIcon(abotIconURl);
        JLabel prgIcon = new JLabel(aboutIcon);
        gbl.setConstraints(prgIcon, gbc);
        this.add(prgIcon);
        gbc.gridx = 1;
        String aboutText =
                "<html><div style='font-size: 16px'>QMC Logic Minimizer</div><br>"
                + wmk.getComponentText("abdVersitonText") + " 1.0<br>"
                + "License: GNU General Public License version 2.0 (GPLv2)<br><br>"
                + "Copyright © 2012:<br>"
                + "&nbsp &nbsp &nbsp PJ - oldradio1993@seznam.cz<br>"
                + "All Rights Reserved</html>";
        JLabel clfaText = new JLabel(aboutText);
        gbl.setConstraints(clfaText, gbc);
        this.add(clfaText);
        gbc.gridy = 1;
        gbc.gridx = 0;
        JButton ok = wmk.newJButton("abdOkButton", "abdOkButton", null, true);
        gbl.setConstraints(ok, gbc);
        this.add(ok);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
    }

    void quit() {
        this.dispose();
    }
}
