package Vues;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Erreurs.Erreur;

/**
 * Created by Jerem on 06/11/2015.
 */
public class ZoneErreur extends JPanel implements ObservateurVue {

    private JTextArea zone_texte = new JTextArea("ZONE ERREUR");

    public ZoneErreur(){
        super();
        zone_texte.setEditable(false);
        zone_texte.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-50, 70));
        JScrollPane scrollPane = new JScrollPane(zone_texte,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane);
    }

    public void miseAJour(){
        zone_texte.setBackground(this.getParent().getBackground());
        HashSet<Erreur> erreurs = Ihm.instance().getControleur().getErreurs();
        String error_text = "";
        for(Erreur e : erreurs){
            error_text += e.getMessage() + "\n";
        }
        zone_texte.setText(error_text);
        this.validate();
    }

}
