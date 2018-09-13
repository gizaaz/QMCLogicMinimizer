/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.main;

import QMCLogicMinimizer.dialogs.AboutDialog;
import QMCLogicMinimizer.dialogs.ChngColHdrDialog;
import QMCLogicMinimizer.dialogs.FontDialog;
import QMCLogicMinimizer.dialogs.NewTableDialog;
import QMCLogicMinimizer.gui.WidgetMaker;
import QMCLogicMinimizer.minimization.Minimizer;
import QMCLogicMinimizer.minimization.PrimeImplicantChart;
import QMCLogicMinimizer.table.ExportToHTML;
import QMCLogicMinimizer.table.LoadTable;
import QMCLogicMinimizer.table.SaveTable;
import QMCLogicMinimizer.table.TableModelMaker;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author PJ
 */
public class Main extends MainFrame {
    //////////////////////////////////////////////////
    // get actual computer locale and initialize WidgetMaker
    //////////////////////////////////////////////////

    private WidgetMaker wmk = new WidgetMaker();
    //////////////////////////////////////////////////
    // create new table dialog object
    //////////////////////////////////////////////////
    private NewTableDialog ntd = new NewTableDialog(this, wmk);
    private JTable table;
    private JScrollPane tableSclPane;
    private JTextArea textOutArea;
    private JMenuItem fileSaveTable;
    private JMenu fileExportTable;
    private JMenu menuBarTable;
    private JMenu menuBarRun;
    private JMenuItem tableEditColHeader;
    private JRadioButtonMenuItem tableLock;
    private JButton toolBarSaveTable;
    private JButton toolBarEditColHeader;
    private JButton toolBarTableFont;
    private JButton toolBarTableBckColor;
    private JButton toolBarLockTable;
    private JButton toolBarMinimize;
    private MouseAdapter tableValueMouseAdapter;
    private MouseAdapter tablePopupMouseAdapter;
    private KeyAdapter tableKeyAdapter;

    Main() {
        this.setSize(1000, 800);
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        // menuBar
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        //////////////////////////////////////////////////
        // menuBar - File
        //////////////////////////////////////////////////        
        JMenu menuBarFile = wmk.newJMenu(menuBar, "menuBarFile", true, true);
        //////////////////////////////////////////////////
        // File - New table
        //////////////////////////////////////////////////  
        JMenuItem fileNewTable = wmk.newJMenuItem(menuBarFile, "fileNewTable", "newIcon.gif", 'N', true);
        menuBarFile.addSeparator();
        fileNewTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewTable();
            }
        });
        //////////////////////////////////////////////////
        // File - Load table
        //////////////////////////////////////////////////  
        JMenuItem fileLoadTable = wmk.newJMenuItem(menuBarFile, "fileLoadTable", "loadIcon.gif", 'O', true);
        fileLoadTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        //////////////////////////////////////////////////
        // File - Save table
        //////////////////////////////////////////////////  
        fileSaveTable = wmk.newJMenuItem(menuBarFile, "fileSaveTable", "saveIcon.gif", 'S', false);
        fileSaveTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTable();
            }
        });
        //////////////////////////////////////////////////
        // File - Export table
        //////////////////////////////////////////////////
        fileExportTable = wmk.newJMenu(menuBar, "fileExportTable", false, true);
        menuBarFile.add(fileExportTable);
        menuBarFile.addSeparator();
        //////////////////////////////////////////////////
        // Export table - to HTML
        //////////////////////////////////////////////////
        JMenuItem exportTableToHtml = wmk.newJMenuItem(fileExportTable, "exportTableToHtml", "htmlIcon.gif", 0, true);
        exportTableToHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportToHtml();
            }
        });
        //////////////////////////////////////////////////
        // File - Quit
        //////////////////////////////////////////////////
        JMenuItem fileQuit = wmk.newJMenuItem(menuBarFile, "fileQuit", "quitIcon.gif", 'Q', true);
        fileQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //////////////////////////////////////////////////
        // menuBar - Table
        //////////////////////////////////////////////////
        menuBarTable = wmk.newJMenu(menuBar, "menuBarTable", false, true);
        //////////////////////////////////////////////////
        // Table - Backgroud color
        //////////////////////////////////////////////////        
        JMenuItem tableBckColor = wmk.newJMenuItem(menuBarTable, "tableBckColor", "colorIcon.gif", 0, true);
        tableBckColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableBckColor();
            }
        });
        //////////////////////////////////////////////////
        // Table - Grid color
        //////////////////////////////////////////////////        
        JMenuItem tableGridColor = wmk.newJMenuItem(menuBarTable, "tableGridColor", "gcolorIcon.gif", 0, true);
        tableGridColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableGridColor();
            }
        });
        //////////////////////////////////////////////////
        // Table - Font
        //////////////////////////////////////////////////        
        JMenuItem tableFont = wmk.newJMenuItem(menuBarTable, "tableFont", "fontIcon.gif", 0, true);
        tableFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableFont();
            }
        });
        menuBarTable.addSeparator();
        //////////////////////////////////////////////////
        // Table - Edit column header
        ////////////////////////////////////////////////// 
        tableEditColHeader = wmk.newJMenuItem(menuBarTable, "tableEditColHeader", "hdrsIcon.gif", 'H', false);
        tableEditColHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chngTableColHeader();
            }
        });
        //////////////////////////////////////////////////
        // Table - Lock
        //////////////////////////////////////////////////         
        tableLock = wmk.newJRadioButtonMenuItem("tableLock", "lockIcon.gif", 'L', true);
        menuBarTable.add(tableLock);
        tableLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockTable();
            }
        });
        //////////////////////////////////////////////////
        // menuBar - Output
        //////////////////////////////////////////////////
        JMenu menuBarOutput = wmk.newJMenu(menuBar, "menuBarOutput", true, true);
        //////////////////////////////////////////////////
        // Output - Font
        //////////////////////////////////////////////////
        JMenuItem outputFont = wmk.newJMenuItem(menuBarOutput, "outputFont", "fontIcon.gif", 0, true);
        outputFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextOutAreaFont();
            }
        });
        //////////////////////////////////////////////////
        // Output - Background color
        //////////////////////////////////////////////////
        JMenuItem outputColor = wmk.newJMenuItem(menuBarOutput, "outputColor", "colorIcon.gif", 0, true);
        outputColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextOutAreaBckColor();
            }
        });
        //////////////////////////////////////////////////
        // menuBar - Run
        //////////////////////////////////////////////////
        menuBarRun = wmk.newJMenu(menuBar, "menuBarRun", false, true);
        //////////////////////////////////////////////////
        // Run - Minimize
        //////////////////////////////////////////////////        
        JMenuItem runMinimize = wmk.newJMenuItem(menuBarRun, "runMinimize", "minIcon.gif", 'M', true);
        runMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minimizer();
            }
        });
        //////////////////////////////////////////////////
        // menuBar - Help
        //////////////////////////////////////////////////
        JMenu menuBarHelp = wmk.newJMenu(menuBar, "menuBarHelp", true, true);
        //////////////////////////////////////////////////
        // Help - About
        //////////////////////////////////////////////////
        JMenuItem helpAbout = wmk.newJMenuItem(menuBarHelp, "helpAbout", "aboutIcon.gif", 0, true);
        helpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        // layout manager
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        // toolBar
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        JToolBar toolBar = new JToolBar();
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(toolBar, gbc);
        this.add(toolBar);
        toolBar.setFloatable(false);
        //////////////////////////////////////////////////
        // toolBar - New Table
        //////////////////////////////////////////////////
        JButton toolBarNewTable = wmk.newJButton(null, "fileNewTable", "newIcon24.gif", true);
        toolBar.add(toolBarNewTable);
        toolBarNewTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewTable();
            }
        });
        //////////////////////////////////////////////////
        // toolBar - Load table
        //////////////////////////////////////////////////
        JButton toolBarLoadTable = wmk.newJButton(null, "fileLoadTable", "loadIcon24.gif", true);
        toolBar.add(toolBarLoadTable);
        toolBarLoadTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        //////////////////////////////////////////////////
        // toolBar - Save table
        //////////////////////////////////////////////////
        toolBarSaveTable = wmk.newJButton(null, "fileSaveTable", "saveIcon24.gif", false);
        toolBar.add(toolBarSaveTable);
        toolBarSaveTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTable();
            }
        });
        toolBar.addSeparator();
        //////////////////////////////////////////////////
        // toolBar - Edit table columnt header
        //////////////////////////////////////////////////
        toolBarEditColHeader = wmk.newJButton(null, "tableEditColHeader", "hdrsIcon24.gif", false);
        toolBar.add(toolBarEditColHeader);
        toolBarEditColHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chngTableColHeader();
            }
        });
        //////////////////////////////////////////////////
        // toolBar - Table font
        //////////////////////////////////////////////////
        toolBarTableFont = wmk.newJButton(null, "tableFont", "fontIcon24.gif", false);
        toolBar.add(toolBarTableFont);
        toolBarTableFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableFont();
            }
        });
        //////////////////////////////////////////////////
        // toolBar - Table background color
        //////////////////////////////////////////////////        
        toolBarTableBckColor = wmk.newJButton(null, "tableBckColor", "colorIcon24.gif", false);
        toolBar.add(toolBarTableBckColor);
        toolBarTableBckColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableBckColor();
            }
        });
        //////////////////////////////////////////////////
        // toolBar - Lock table
        //////////////////////////////////////////////////        
        toolBarLockTable = wmk.newJButton(null, "tableLock", "lockIcon24.gif", false);
        toolBarLockTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableLock.isSelected()) {
                    tableLock.setSelected(false);
                } else {
                    tableLock.setSelected(true);
                }
                lockTable();
            }
        });
        toolBar.add(toolBarLockTable);
        toolBar.addSeparator();
        //////////////////////////////////////////////////
        // toolBar - Minimize
        //////////////////////////////////////////////////        
        toolBarMinimize = wmk.newJButton(null, "runMinimize", "minIcon24.gif", false);
        toolBar.add(toolBarMinimize);
        toolBarMinimize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minimizer();
            }
        });
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        // main table (JTable)
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        gbc.weighty = 0.6;
        gbc.gridy = 1;
        table = new JTable() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tableSclPane = wmk.newJScrollPane(table);
        gbl.setConstraints(tableSclPane, gbc);
        this.add(tableSclPane);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        table.setForeground(Color.BLACK);
        //////////////////////
        // table popup menu
        //////////////////////
        final JPopupMenu tablePopupMenu = wmk.newJPopupMenu();
        JMenuItem tablePopupFont = wmk.newJMenuItem(null, "tableFont", "fontIcon.gif", 0, true);
        tablePopupFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTableFont();
            }
        });
        JMenuItem tablePopupEditColHeader = wmk.newJMenuItem(null, "tableEditColHeader", "hdrsIcon.gif", 'H', true);
        tablePopupEditColHeader.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chngTableColHeader();
            }
        });
        tablePopupMenu.add(tablePopupEditColHeader);
        tablePopupMenu.add(tablePopupFont);
        //////////////////////
        // table mouse adapter
        //////////////////////
        tableValueMouseAdapter = new MouseAdapter() {
            int row = 0;
            int column = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0 && e.getButton() == 1) {
                    if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
                        tableAction();
                    } else {
                        row = table.getSelectedRow();
                        column = table.getSelectedColumn();
                        tableEditColHeader.setEnabled(true);
                        toolBarEditColHeader.setEnabled(true);
                    }
                }
            }
        };
        table.addMouseListener(tableValueMouseAdapter);
        tablePopupMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0 && e.getButton() == 1) {
                    tableEditColHeader.setEnabled(true);
                    toolBarEditColHeader.setEnabled(true);
                }
                if (e.getButton() == 3) {
                    if (table.isCellSelected(table.getSelectedRow(), table.getSelectedColumn())) {
                        tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        };
        table.addMouseListener(tablePopupMouseAdapter);
        //////////////////////
        // table key adapter
        //////////////////////
        tableKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                tableEditColHeader.setEnabled(true);
                toolBarEditColHeader.setEnabled(true);
                if (e.getKeyCode() == 32) {
                    tableAction();
                }
            }
        };
        table.addKeyListener(tableKeyAdapter);
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        // label "Output:"
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        gbc.weighty = 0.0;
        JLabel label1 = wmk.newJLabel("labelOutput");
        gbc.gridy = 2;
        gbl.setConstraints(label1, gbc);
        this.add(label1);
        ////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////
        // text out (JTextArea)
        ////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////
        gbc.weighty = 0.4;
        gbc.gridy = 3;
        textOutArea = new JTextArea();
        JScrollPane textOutAreaSclPane = new JScrollPane(textOutArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gbl.setConstraints(textOutAreaSclPane, gbc);
        this.add(textOutAreaSclPane);
        textOutArea.setEditable(false);
        textOutArea.setForeground(Color.black);
        final JPopupMenu textOutAreaPopupMenu = wmk.newJPopupMenu();
        JMenuItem textOutAreaPopupFont = wmk.newJMenuItem(null, "textOutAreaPopupFont", "fontIcon.gif", 0, true);
        textOutAreaPopupMenu.add(textOutAreaPopupFont);
        textOutAreaPopupFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextOutAreaFont();
            }
        });
        JMenuItem textOutAreaPopupColor = wmk.newJMenuItem(null, "outputColor", "colorIcon.gif", 0, true);
        textOutAreaPopupMenu.add(textOutAreaPopupColor);
        textOutAreaPopupColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTextOutAreaBckColor();
            }
        });
        textOutArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3) {
                    textOutAreaPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void createNewTable() {
        ntd.cancelFlag = true;
        ntd.show();
        if (!ntd.isCanceled()) {
            TableModelMaker mm = new TableModelMaker();
            DefaultTableModel model = new DefaultTableModel(mm.rowsData(ntd.getInpVar(), ntd.getOutVar(), ntd.getRows()), mm.columnsHeaders(ntd.getInpVar(), ntd.getOutVar()));
            table.setModel(model);
            table.setRowHeight(30);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
            centerRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);
            for (int c = 0; c < ntd.getInpVar(); c++) {
                table.getColumnModel().getColumn(c).setPreferredWidth(30);
                table.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
            }
            for (int c = ntd.getInpVar(); c < ntd.getInpVar() + ntd.getOutVar(); c++) {
                table.getColumnModel().getColumn(c).setPreferredWidth(60);
                table.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
            }
            newTableCreated();
        }
    }

    private void newTableCreated() {
        textOutArea.setText(null);
        fileSaveTable.setEnabled(true);
        fileExportTable.setEnabled(true);
        menuBarTable.setEnabled(true);
        tableEditColHeader.setEnabled(false);
        menuBarRun.setEnabled(true);
        toolBarSaveTable.setEnabled(true);
        toolBarEditColHeader.setEnabled(false);
        toolBarTableFont.setEnabled(true);
        toolBarTableBckColor.setEnabled(true);
        toolBarLockTable.setEnabled(true);
        toolBarMinimize.setEnabled(true);
        ListData lm = new ListData(ntd.getRows());
        JList rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(30);
        rowHeader.setCellRenderer(new RowHeaderRenderer(table));
        JTableHeader header = table.getTableHeader();
        rowHeader.setBackground(header.getBackground());
        tableSclPane.setRowHeaderView(rowHeader);

    }

    private void tableAction() {
        int col = table.getSelectedColumn();
        int row = table.getSelectedRow();
        Object cellDataObject = table.getValueAt(row, col);
        String cellData = cellDataObject.toString();
        if (ntd.getRows() == Math.pow(2, ntd.getInpVar())) {
            if (col > ntd.getInpVar() - 1) {
                if ("1".equals(cellData)) {
                    table.setValueAt("X", row, col);
                }
                if ("X".equals(cellData)) {
                    table.setValueAt("0", row, col);
                }
                if ("0".equals(cellData)) {
                    table.setValueAt("1", row, col);
                }
            }
        } else {
            if ("0".equals(cellData)) {
                table.setValueAt("1", row, col);
            }
            if (col > ntd.getInpVar() - 1) {
                if ("1".equals(cellData)) {
                    table.setValueAt("X", row, col);
                }
                if ("X".equals(cellData)) {
                    table.setValueAt("0", row, col);
                }
            } else {
                if ("1".equals(cellData)) {
                    table.setValueAt("0", row, col);
                }
            }
        }
    }

    private void lockTable() {
        if (tableLock.isSelected()) {
            table.removeMouseListener(tableValueMouseAdapter);
            table.removeKeyListener(tableKeyAdapter);
        } else {
            table.addMouseListener(tableValueMouseAdapter);
            table.addKeyListener(tableKeyAdapter);
        }
    }

    private void chngTableColHeader() {
        ChngColHdrDialog chngDial = new ChngColHdrDialog(this, wmk, table, ntd);
        chngDial.show();
        if (chngDial.getNewHeader() != null) {
            table.getColumnModel().getColumn(table.getSelectedColumn()).setHeaderValue(chngDial.getNewHeader());
            table.addNotify();
        }
    }

    private void setTableBckColor() {
        Color bckColor = JColorChooser.showDialog(null, wmk.getComponentText("tbcdSuperText"), table.getBackground());
        if (bckColor != null) {
            table.setBackground(bckColor);
        }
    }

    private void setTableGridColor() {
        Color gridColor = JColorChooser.showDialog(null, wmk.getComponentText("tgcdSuperText"), table.getGridColor());
        if (gridColor != null) {
            table.setGridColor(gridColor);
        }
    }

    private void setTableFont() {
        FontDialog fntd = new FontDialog(this, wmk, table.getFont(), table.getForeground(), 36);
        fntd.show();
        if (fntd.getNewFont() != null) {
            table.setFont(fntd.getNewFont());
            table.setForeground(fntd.getNewColor());
        }
    }

    private void saveTable() {
        SaveTable st = new SaveTable(table, ntd, wmk);
    }

    private void loadTable() {
        LoadTable ld = new LoadTable(table, ntd, wmk);
        if (ld.getStatus()) {
            newTableCreated();
        }
    }

    private void setTextOutAreaFont() {
        FontDialog fntd = new FontDialog(this, wmk, textOutArea.getFont(), textOutArea.getForeground(), 96);
        fntd.show();
        if (fntd.getNewFont() != null) {
            textOutArea.setFont(fntd.getNewFont());
            textOutArea.setForeground(fntd.getNewColor());
        }
    }

    private void setTextOutAreaBckColor() {
        Color backgroundColor = JColorChooser.showDialog(null, wmk.getComponentText("obcdSuperText"), textOutArea.getBackground());
        if (backgroundColor != null) {
            textOutArea.setBackground(backgroundColor);
        }
    }
    ArrayList<ArrayList<String[]>> minSolution = new ArrayList<ArrayList<String[]>>();

    private void minimizer() {
        minSolution.clear();
        for (int output = ntd.getInpVar(); output < (ntd.getInpVar() + ntd.getOutVar()); output++) {
            ArrayList<String[]> compInputData = new ArrayList<String[]>();
            ArrayList<String[]> tableMinterms = new ArrayList<String[]>();
            ////////////////////////////
            // prvni napleni dyn. pole, data pro proces minimalizace
            ////////////////////////////
            int log1 = 0;
            for (int i = 0; i < ntd.getRows(); i++) {
                if (!"0".equals(table.getValueAt(i, output).toString())) {
                    String minTerm[] = new String[ntd.getInpVar() + 1];
                    for (int j = 0; j < ntd.getInpVar(); j++) {
                        minTerm[j] = table.getValueAt(i, j).toString();
                    }
                    if ("X".equals(table.getValueAt(i, output).toString())) {
                        compInputData.add(minTerm);
                    } else {
                        log1++;
                        tableMinterms.add(minTerm);
                        compInputData.add(minTerm);
                    }
                }
            }
            ////////////////////////////
            // volani minimalizatoru
            ////////////////////////////
            Minimizer minimizer = new Minimizer(compInputData);
            ////////////////////////////
            // volani tabulky pokryti
            ////////////////////////////        
            PrimeImplicantChart pic = new PrimeImplicantChart(tableMinterms, minimizer.getMinimized());
            minSolution.add(pic.getMinSolution());
        }
        writeToTextOutArea();
    }

    private void writeToTextOutArea() {
        String outData = "";
        for (int i = 0; i < ntd.getOutVar(); i++) {
            outData += table.getColumnModel().getColumn(ntd.getInpVar() + i).getHeaderValue().toString() + "  =  ";
            ArrayList<String[]> minOutData = minSolution.get(i);
            if (minOutData.isEmpty()) {
                outData += "0";
            } else {
                for (int j = 0; j < minOutData.size(); j++) {
                    String[] primImpl = minOutData.get(j);
                    int dash = 0;
                    for (int k = 0; k < primImpl.length - 1; k++) {
                        switch (primImpl[k].charAt(0)) {
                            case 49:
                                outData += table.getColumnModel().getColumn(k).getHeaderValue().toString() + " ";
                                break;
                            case 48:
                                outData += table.getColumnModel().getColumn(k).getHeaderValue().toString() + "' ";
                                break;
                            case 45:
                                dash++;
                                break;
                        }
                    }
                    if (dash == ntd.getInpVar()) {
                        outData += "1";
                    }
                    if (j != minOutData.size() - 1) {
                        outData += " +  ";
                    }
                }
            }
            outData += "\n";
        }
        textOutArea.setText(outData);
    }

    private void exportToHtml() {
        ExportToHTML export = new ExportToHTML(table, ntd);
    }

    private void showAboutDialog() {
        AboutDialog abt = new AboutDialog(this, wmk);
        abt.show();
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

    RowHeaderRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setFont(header.getFont());
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
////////////////////////////
// trida pro nastaveni bocniho panelu u tabulky s cilem radku
////////////////////////////
class ListData extends AbstractListModel {

    String[] rowNumbers;

    public ListData(int rows) {
        rowNumbers = new String[rows];
        for (int i = 0; i < rows; i++) {
            rowNumbers[i] = Integer.toString(i);
        }
    }

    @Override
    public int getSize() {
        return rowNumbers.length;
    }

    @Override
    public Object getElementAt(int index) {
        return rowNumbers[index];
    }
}
