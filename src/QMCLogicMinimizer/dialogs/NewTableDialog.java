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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author PJ
 */
public class NewTableDialog extends JDialog {

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
                creatTable();
            }
        }, enter, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootpane;
    }
    private JSpinner inpVarSpnr;
    private JSpinner outVarSpnr;
    private JSpinner rowsSpnr;
    public boolean cancelFlag = true;
    public int inpVarInt;
    public int outVarInt;
    public int rowsInt;

    public NewTableDialog(JFrame owner, WidgetMaker wmk) {
        super(owner, wmk.getComponentText("ntdSuperText"), true);
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        JLabel label1 = wmk.newJLabel("ntdInpVarLabel");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(label1, gbc);
        this.add(label1);
        inpVarSpnr = wmk.newJSpinner(wmk.newSpinnerNumberModel(2, 2, 16, 1));
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        gbl.setConstraints(inpVarSpnr, gbc);
        this.add(inpVarSpnr);
        inpVarSpnr.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inpVarSpnrStateChanged(e);
            }
        });
        JLabel label2 = wmk.newJLabel("ntdOutVarLabel");
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.weightx = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbl.setConstraints(label2, gbc);
        this.add(label2);
        outVarSpnr = wmk.newJSpinner(wmk.newSpinnerNumberModel(1, 1, 16, 1));
        outVarSpnr.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Object outVarObj = outVarSpnr.getValue();
                String outVarStr = outVarObj.toString();
                outVarInt = Integer.parseInt(outVarStr);
            }
        });
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        gbl.setConstraints(outVarSpnr, gbc);
        this.add(outVarSpnr);
        JLabel label3 = wmk.newJLabel("ntdRowsLabel");
        label3.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.weightx = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbl.setConstraints(label3, gbc);
        this.add(label3);
        rowsSpnr = wmk.newJSpinner(wmk.newSpinnerNumberModel(2, 1, 2, 1));
        rowsSpnr.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Object rowsObj = rowsSpnr.getValue();
                String rowsStr = rowsObj.toString();
                rowsInt = Integer.parseInt(rowsStr);
            }
        });
        gbc.weightx = 1.0;
        gbc.gridx = 1;
        gbl.setConstraints(rowsSpnr, gbc);
        this.add(rowsSpnr);
        JButton ctBtn = wmk.newJButton("ntdCreateTableButton", "ntdCreateTableButton", null, true);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbl.setConstraints(ctBtn, gbc);
        this.add(ctBtn);
        ctBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatTable();
            }
        });
        JButton cnlBtn = wmk.newJButton("ntdCancelButton", "ntdCancelButton", null, true);
        gbc.gridx = 1;
        gbl.setConstraints(cnlBtn, gbc);
        this.add(cnlBtn);
        cnlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        this.setResizable(false);
        this.setLocation(200, 200);
        this.setSize(250, 180);
        Object inpVarObj = inpVarSpnr.getValue();
        String inpVarStr = inpVarObj.toString();
        double inpVarDouble = Double.parseDouble(inpVarStr);
        double maxRowsDouble = Math.pow(2, inpVarDouble);
        int maxRows = (int) maxRowsDouble;
        SpinnerModel sm = new SpinnerNumberModel(maxRows, 2, maxRows, 1);
        rowsSpnr.setModel(sm);
        inpVarObj = inpVarSpnr.getValue();
        inpVarStr = inpVarObj.toString();
        inpVarInt = Integer.parseInt(inpVarStr);
        Object outVarObj = outVarSpnr.getValue();
        String outVarStr = outVarObj.toString();
        outVarInt = Integer.parseInt(outVarStr);
        Object rowsObj = rowsSpnr.getValue();
        String rowsStr = rowsObj.toString();
        rowsInt = Integer.parseInt(rowsStr);
    }

    public void creatTable() {
        cancelFlag = false;
        this.dispose();
    }

    public void cancel() {
        this.dispose();
    }

    public Boolean isCanceled() {
        return cancelFlag;
    }

    public int getInpVar() {
        return inpVarInt;
    }

    public int getOutVar() {
        return outVarInt;
    }

    public int getRows() {
        return rowsInt;
    }

    private void inpVarSpnrStateChanged(ChangeEvent e) {
        Object inpVarObj = inpVarSpnr.getValue();
        String inpVarStr = inpVarObj.toString();
        double inpVarDouble = Double.parseDouble(inpVarStr);
        double maxRowsDouble = Math.pow(2, inpVarDouble);
        int maxRows = (int) maxRowsDouble;
        SpinnerModel sm = new SpinnerNumberModel(maxRows, 2, maxRows, 1);
        rowsSpnr.setModel(sm);
        inpVarInt = Integer.parseInt(inpVarStr);
        Object rowsObj = rowsSpnr.getValue();
        String rowsStr = rowsObj.toString();
        rowsInt = Integer.parseInt(rowsStr);
    }
}
