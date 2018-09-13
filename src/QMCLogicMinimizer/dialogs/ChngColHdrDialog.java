/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.dialogs;

import QMCLogicMinimizer.gui.WidgetMaker;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author PJ
 */
public class ChngColHdrDialog extends JDialog {

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootpane = super.createRootPane();
        KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        rootpane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newHeader = null;
                close();
            }
        }, escape, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootpane;
    }
    private JTextField tf;
    private boolean firstKey = false;
    private boolean firstClick = false;
    private String newHeader = null;

    public ChngColHdrDialog(JFrame owner, final WidgetMaker wmk, final JTable table, final NewTableDialog ntd) {
        super(owner, wmk.getComponentText("cchdSuperText"), true);
        this.setLayout(new GridLayout(2, 1, 5, 5));
        tf = new JTextField();
        this.add(tf);
        tf.setText(table.getColumnModel().getColumn(table.getSelectedColumn()).getHeaderValue().toString());
        tf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!firstClick) {
                    tf.setText(null);
                }
                firstClick = true;
            }
        });
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    changeHeader(wmk, ntd, table);
                } else {
                    if (e.getKeyCode() != 37 && e.getKeyCode() != 39) {
                        if (!firstKey) {
                            tf.setText(null);
                            firstKey = true;
                        }
                    } else {

                        firstKey = true;
                    }
                }
            }
        });
        JButton okBtn = wmk.newJButton("cchdOkButtonn", "cchdOkButtonn", null, true);
        this.add(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeHeader(wmk, ntd, table);
            }
        });
        okBtn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    changeHeader(wmk, ntd, table);
                }

            }
        });
        this.pack();
        this.setResizable(false);
        this.setLocation(200, 200);
        this.setSize(260, 100);
    }

    private void changeHeader(WidgetMaker wmk, NewTableDialog ntd, JTable table) {
        if (!"".equals(tf.getText())) {
            if (tf.getText().length() <= 6) {
                boolean ok = true;
                for (int i = 0; i < tf.getText().length(); i++) {
                    char character = tf.getText().charAt(i);
                    if (character < 48 || character > 57 && character < 65 || character > 90 && character < 97 || character > 122) {
                        JOptionPane.showMessageDialog(null, wmk.getComponentText("cchdErr1"), wmk.getComponentText("cchdErrSuper"), 0);
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    for (int i = 0; i < ntd.getInpVar() + ntd.getOutVar(); i++) {
                        if (table.getColumnModel().getColumn(i).getHeaderValue().toString().equals(tf.getText()) && i != table.getSelectedColumn()) {
                            JOptionPane.showMessageDialog(null, wmk.getComponentText("cchdErr4"), wmk.getComponentText("cchdErrSuper"), 0);
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        newHeader = tf.getText();
                        close();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, wmk.getComponentText("cchdErr2"), wmk.getComponentText("cchdErrSuper"), 0);
            }
        } else {
            JOptionPane.showMessageDialog(null, wmk.getComponentText("cchdErr3"), wmk.getComponentText("cchdErrSuper"), 0);
        }
    }

    public String getNewHeader() {
        return newHeader;
    }

    public void close() {
        this.dispose();
    }
}
