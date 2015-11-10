package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import ElementsDiagramme.Etat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationTransition extends FenetrePopup implements ActionListener{

    private JComboBox liste_etats;
    private JLabel liste_label = new JLabel("Liste des etats de destination");
    private JLabel etiquette_label = new JLabel("Etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    private EnumTransition type;
    private EtatGraph etat_source;

    public CreationTransition(EtatGraph etat_source, EnumTransition type) throws Exception {
        super();
        this.type = type;
        this.etat_source = etat_source;

        JPanel centered = new JPanel();
        etiquette_transition.setPreferredSize(new Dimension(150, 30));

        GridLayout g = new GridLayout(3,2);
        g.setHgap(15);
        g.setVgap(15);

        HashSet<EtatGraph> liste_elements = Ihm.instance().getControleur().getStatesFromSameConteneur(etat_source);
        String[] liste_noms_etats = new String[liste_elements.size()];

        int i=0;
        for(EtatGraph e: liste_elements){
            liste_noms_etats[i] = e.getNom();
            i++;
        }
        liste_etats = new JComboBox(liste_noms_etats);

        getPan().add(liste_label);
        getPan().add(liste_etats);
        getPan().add(etiquette_label);
        getPan().add(etiquette_transition);

        this.valider.addActionListener(this);
        getPan().add(valider);

        getPan().setLayout(g);
        this.add(getPan());

    }

    public void actionPerformed(ActionEvent arg0){
        EnumEtat type_etat = etat_source.getType();
        if(type_etat == EnumEtat.INIT)
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(),etat_source,(String)liste_etats.getSelectedItem());
        else if(type_etat == EnumEtat.SIMPLE)
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(), etat_source, (String) liste_etats.getSelectedItem());
        else
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(), etat_source, (String) liste_etats.getSelectedItem());
        this.dispose();
    }
}
