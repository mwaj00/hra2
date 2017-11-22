/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.IOException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import static org.omg.CORBA.ORB.init;

/**
 *
 * @author Julietta
 */
public class OknoNapovedy {
    
    private JDialog oknoNapovedyDialog;
    private JEditorPane vystupEditorPane;

    private URL umisteniSouboru;
    
     public OknoNapovedy(String soubor) {

        umisteniSouboru = this.getClass().getResource(soubor);

        init();

    }
     
     private void init() {
        oknoNapovedyDialog = new JDialog();
        oknoNapovedyDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        vystupEditorPane = new JEditorPane();
        vystupEditorPane.setEditable(false);
        oknoNapovedyDialog.add(new JScrollPane(vystupEditorPane));
        try {
            vystupEditorPane.setPage(umisteniSouboru);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "tož já bych ti poradil, ale nemam tu nikde nápovědu",
                    "maš asik smůlu",
                    JOptionPane.ERROR_MESSAGE);
        }
        oknoNapovedyDialog.setLocation(700, 200);
        oknoNapovedyDialog.setSize(400, 600);
    }
    
     public void setVisible(boolean viditelnost) {
        oknoNapovedyDialog.setVisible(viditelnost);
    }
}
