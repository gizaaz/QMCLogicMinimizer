/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.table;

import QMCLogicMinimizer.dialogs.NewTableDialog;
import QMCLogicMinimizer.dialogs.OSFileDialog;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;

/**
 *
 * @author petr
 */
public class ExportToHTML {

    public ExportToHTML(JTable table, NewTableDialog ntd) {
        OSFileDialog sd = new OSFileDialog(".html", "HTML files");
        String fileName = sd.fileName(0);
        if (!fileName.toLowerCase().endsWith(".html") && !"canceled".equals(fileName)) {
            fileName = fileName + ".html";
        }
        if (!"canceled".equals(fileName)) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
                Color bckColor = table.getBackground();
                int bckRed = bckColor.getRed();
                int bckGreen = bckColor.getGreen();
                int bckBlue = bckColor.getBlue();
                Color grdColor = table.getGridColor();
                int grdRed = grdColor.getRed();
                int grdGreen = grdColor.getGreen();
                int grdBlue = grdColor.getBlue();
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////
                // table style
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////               
                out.write("<html>");
                out.newLine();
                out.write("<table style='border-collapse: collapse; background-color: rgb(");
                out.write(Integer.toString(bckRed));
                out.write(",");
                out.write(Integer.toString(bckGreen));
                out.write(",");
                out.write(Integer.toString(bckBlue));
                out.write("); ");
                out.write("border: 1px solid rgb(");
                out.write(Integer.toString(grdRed));
                out.write(",");
                out.write(Integer.toString(grdGreen));
                out.write(",");
                out.write(Integer.toString(grdBlue));
                out.write(");");
                String rgb = Integer.toHexString(table.getForeground().getRGB());
                rgb = rgb.substring(2, rgb.length());
                out.write(" color: #" + rgb + ";'>");
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////
                // header style
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////              
                out.newLine();
                out.write("<tr>");
                out.newLine();
                int seq = ntd.getInpVar() + ntd.getOutVar();
                int i = 0;
                while (i < seq) {
                    Object objHeader = table.getColumnModel().getColumn(i).getHeaderValue();
                    String header = objHeader.toString();
                    int intWidth = table.getColumnModel().getColumn(i).getPreferredWidth();
                    String width = Integer.toString(intWidth);
                    int intHeight = table.getRowHeight();
                    String height = Integer.toString(intHeight);
                    i++;
                    out.write("<th style='width:");
                    out.write(width);
                    out.write("px; ");
                    out.write("height:");
                    out.write(height);
                    out.write("px; text-align: center;");
                    out.write("border-style: solid solid double; border-width: 1px 1px 4px; border-bottom: 4px double rgb(");
                    out.write(Integer.toString(grdRed));
                    out.write(",");
                    out.write(Integer.toString(grdGreen));
                    out.write(",");
                    out.write(Integer.toString(grdBlue));
                    out.write(");'><b>");
                    out.write(header);
                    out.write("</b></th>");
                }
                out.write("</tr>");
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////
                // table data
                ////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////                   
                int rNmb = ntd.getRows();
                int j = 0;
                while (j < rNmb) {
                    out.newLine();
                    out.write("<tr>");
                    seq = ntd.getInpVar() + ntd.getOutVar();
                    i = 0;
                    while (i < seq) {
                        int intWidth2 = table.getColumnModel().getColumn(i).getPreferredWidth();
                        String width2 = Integer.toString(intWidth2);
                        int intHeight2 = table.getRowHeight();
                        String height2 = Integer.toString(intHeight2);
                        out.write("<td style='width:");
                        out.write(width2);
                        out.write("px; ");
                        out.write("height:");
                        out.write(height2);
                        out.write("px; text-align: center;");
                        out.write("border: 1px solid rgb(");
                        out.write(Integer.toString(grdRed));
                        out.write(",");
                        out.write(Integer.toString(grdGreen));
                        out.write(",");
                        out.write(Integer.toString(grdBlue));
                        out.write("); font-size: " + table.getFont().getSize() + "px;'>");
                        out.write(tableReader(j, i, table));
                        out.write("</td>");
                        i++;
                    }
                    j++;
                    out.write("</tr>");
                }
                out.newLine();
                out.write("</table>");
                out.newLine();
                out.write("</html>");
                out.close();
            } catch (IOException e) {
                System.out.println("Export table error !");
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
