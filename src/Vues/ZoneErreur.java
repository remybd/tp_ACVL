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
        zone_texte.setEditable(false);
        zone_texte.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-50, 70));
        JScrollPane scrollPane = new JScrollPane(zone_texte,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.set(Toolkit.getDefaultToolkit().getScreenSize().width, 100);
        this.add(scrollPane);
    }

    public void miseAJour(){
        zone_texte.setBackground(this.getParent().getBackground());
        HashSet<Erreur> erreurs = Ihm.instance().getControleur().getErreurs();
        String error_text = "";
        for(Erreur e : erreurs){
            System.out.println(" Nom : " + e.getNom());
            //TODO enlever le comentaire en dessous
            error_text += e.getMessage() + "\n";
        }
        zone_texte.setText(error_text);
        this.validate();
    }

}
