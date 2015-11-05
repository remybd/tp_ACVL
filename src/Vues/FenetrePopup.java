package Vues;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jerem on 03/11/2015.
 */
public class FenetrePopup extends JFrame{

    private JPanel pan = new JPanel();
    private ControlleurDiagramme controlleur = null;

    public FenetrePopup(ControlleurDiagramme controlleur){
        pan.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
        this.controlleur = controlleur;
    }

    public JPanel getPan() {
        return pan;
    }

    public void setPan(JPanel pan) {
        this.pan = pan;
    }
}