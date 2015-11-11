package Vues;

import Erreurs.Erreur;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Jerem on 06/11/2015.
 */
public class ZoneErreur extends JPanel implements ObservateurVue {

    private JTextArea zone_texte = new JTextArea("ZONE ERREUR");

    public ZoneErreur(){
        super();
        //this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, );
        zone_texte.setBackground(Color.LIGHT_GRAY);
        zone_texte.setEditable(false);
        this.add(zone_texte);
    }

    public void miseAJour(){
        HashSet<Erreur> erreurs = Ihm.instance().getControleur().getErreurs();
        String error_text = "";
        System.out.println(" sIZE / " + erreurs.isEmpty());
        for(Erreur e : erreurs){
            System.out.println(" Nom : " + e.getNom());
            error_text += e.getMessage() + "\n";
        }
        System.out.println("Erreur ::::" + error_text);
        zone_texte.setText(error_text);
        this.validate();
    }

}
