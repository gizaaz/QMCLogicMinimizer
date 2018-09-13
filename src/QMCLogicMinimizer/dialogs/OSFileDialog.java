/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.dialogs;

/**
 *
 * @author petr
 */
import QMCLogicMinimizer.main.MainFrame;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class OSFileDialog extends MainFrame {

    private String fileType;
    private String fileTypeView;
    private JFileChooser fd;

    public OSFileDialog(String format, String view) {
        fileType = format;
        fileTypeView = view;
    }

    public String fileName(int dialogType) {
        fd = new JFileChooser();
        fd.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(fileType)
                        || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return fileTypeView;
            }
        });
        switch (dialogType) {
            case 0:
                if (fd.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    return fd.getSelectedFile().getAbsolutePath();
                }
                break;

            case 1:
                if (fd.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    return fd.getSelectedFile().getAbsolutePath();
                }
                break;
        }
        return "canceled";
    }
}
