/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.table;

import QMCLogicMinimizer.dialogs.NewTableDialog;
import QMCLogicMinimizer.dialogs.OSFileDialog;
import QMCLogicMinimizer.gui.WidgetMaker;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author petr
 */
public class LoadTable {

    private boolean status = false;

    public LoadTable(JTable table, NewTableDialog ntd, WidgetMaker wmk) {
        OSFileDialog lf = new OSFileDialog(".clfa", wmk.getComponentText("sltdFormatDescription"));
        String fileName = lf.fileName(1);
        if (!"canceled".equals(fileName)) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                int n = Integer.parseInt(in.readLine());
                ntd.inpVarInt = n;
                n = Integer.parseInt(in.readLine());
                ntd.outVarInt = n;
                String s[] = new String[ntd.inpVarInt + ntd.outVarInt];
                n = Integer.parseInt(in.readLine());
                ntd.rowsInt = n;
                in.readLine();
                for (int i = 0; i < (ntd.inpVarInt + ntd.outVarInt); i++) {
                    s[i] = in.readLine();
                }
                in.readLine();
                String radek[] = new String[ntd.rowsInt];
                for (int i = 0; i < ntd.rowsInt; i++) {
                    radek[i] = in.readLine();
                }
                char ch[][] = new char[ntd.rowsInt][ntd.inpVarInt + ntd.outVarInt];
                for (int i = 0; i < ntd.rowsInt; i++) {
                    for (int j = 0; j < (ntd.inpVarInt + ntd.outVarInt); j++) {
                        ch[i][j] = radek[i].charAt(j);
                    }
                }
                int columnInt = ntd.inpVarInt + ntd.outVarInt;
                DefaultTableModel gg = new DefaultTableModel();
                table.setRowHeight(30);
                for (int i = 0; i < (ntd.inpVarInt + ntd.outVarInt); i++) {
                    gg.addColumn(s[i]);
                }
                String[] data = new String[columnInt];
                for (int i = 0; i < ntd.rowsInt; i++) {
                    for (int j = 0; j < columnInt; j++) {
                        data[j] = Character.toString(ch[i][j]);
                    }
                    gg.addRow(data);
                }
                table.setModel(gg);
                table.setRowHeight(30);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                centerRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);
                for (int c = 0; c < ntd.inpVarInt; c++) {
                    table.getColumnModel().getColumn(c).setPreferredWidth(30);
                    table.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
                }
                for (int c = ntd.inpVarInt; c < ntd.outVarInt + ntd.inpVarInt; c++) {
                    table.getColumnModel().getColumn(c).setPreferredWidth(60);
                    table.getColumnModel().getColumn(c).setCellRenderer(centerRenderer);
                }
                status = true;
                in.close();
            } catch (IOException e) {
                System.out.println("Load table error !");
            }
        }
    }

    public boolean getStatus() {
        return status;
    }
}
