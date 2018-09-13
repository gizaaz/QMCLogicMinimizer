/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.table;

import QMCLogicMinimizer.dialogs.NewTableDialog;
import QMCLogicMinimizer.dialogs.OSFileDialog;
import QMCLogicMinimizer.gui.WidgetMaker;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;

/**
 *
 * @author petr
 */
public final class SaveTable {

    public SaveTable(JTable table, NewTableDialog ntd, WidgetMaker wmk) {
        OSFileDialog sf = new OSFileDialog(".clfa", wmk.getComponentText("sltdFormatDescription"));
        String fileName = sf.fileName(0);
        if (!fileName.toLowerCase().endsWith(".clfa") && !"canceled".equals(fileName)) {
            fileName = fileName + ".clfa";
        }
        if (!"canceled".equals(fileName)) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
                int rowsNb = ntd.getRows();
                int cNb = ntd.getInpVar() + ntd.getOutVar();
                out.write(Integer.toString(ntd.getInpVar()));
                out.newLine();
                out.write(Integer.toString(ntd.getOutVar()));
                out.newLine();
                out.write(Integer.toString(ntd.getRows()));
                out.newLine();
                out.newLine();
                String header;
                for (int i = 0; i < (ntd.getInpVar() + ntd.getOutVar()); i++) {
                    header = table.getColumnModel().getColumn(i).getHeaderValue().toString();
                    out.write(header);
                    out.newLine();
                }
                out.newLine();
                for (int i = 0; i < rowsNb; i++) {
                    for (int j = 0; j < cNb; j++) {
                        out.write(tableReader(i, j, table));
                    }
                    if (!(rowsNb == i + 1)) {
                        out.newLine();
                    }
                }
                out.close();
            } catch (IOException e) {
                System.out.println("Save table error!");
            }
        }
    }

    private String tableReader(int row, int column, JTable table) {
        Object tableData;
        tableData = table.getValueAt(row, column);
        String tblData = tableData.toString();
        return tblData;
    }
}
