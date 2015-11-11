package Vues;

import Erreurs.Erreur;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Created by Jerem on 06/11/2015.
 */
public class ZoneErreur extends JPanel implements ObservateurVue {

    private JLabel zone_texte = new JLabel("ZONE ERREUR");

    public ZoneErreur(){
        super();
        //this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, );
        this.add(zone_texte);
    }

    public void miseAJour(){
        HashSet<Erreur> erreurs = Ihm.instance().getControleur().getErreurs();
        //for(Hash)
    }

}
