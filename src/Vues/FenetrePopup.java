package Vues;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by Jerem on 03/11/2015.
 */
public class FenetrePopup extends JFrame{

    private JPanel pan = new JPanel();

    public FenetrePopup(){
        pan.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
    }

    public JPanel getPan() {
        return pan;
    }

    public void setPan(JPanel pan) {
        this.pan = pan;
    }
}