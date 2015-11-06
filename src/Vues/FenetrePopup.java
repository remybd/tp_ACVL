package Vues;

import Controleurs.ControleurDiagramme;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jerem on 03/11/2015.
 */
public class FenetrePopup extends JFrame{

    private JPanel pan = new JPanel();
    private ControleurDiagramme controleur = null;

    public FenetrePopup(ControleurDiagramme controleur){
        pan.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
        this.controleur = controleur;
    }

    public JPanel getPan() {
        return pan;
    }

    public void setPan(JPanel pan) {
        this.pan = pan;
    }
}