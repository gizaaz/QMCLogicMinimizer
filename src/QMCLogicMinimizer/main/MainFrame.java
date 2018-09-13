/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMCLogicMinimizer.main;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author OK3PJ
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        super.setTitle("QMC Logic Minimizer");
        this.setLayout(new FlowLayout());
        this.setExtendedState(MainFrame.MAXIMIZED_BOTH);
        java.net.URL prgIcon = MainFrame.class.getResource("/QMCLogicMinimizer/icons/qmclm.gif");
        this.setIconImage(new ImageIcon(prgIcon).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}