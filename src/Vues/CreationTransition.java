package Vues;

import ElementsDiagramme.EnumTransition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationTransition extends FenetrePopup implements ActionListener{

    private JComboBox liste_etats = new JComboBox();
    private JLabel liste_label = new JLabel("Liste des etats de destination");
    private JLabel etiquette_label = new JLabel("Etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    private EnumTransition type;
    private EtatGraph etat_source;

    public CreationTransition(EtatGraph etat_source, EnumTransition type){
        super();
        this.type = type;
        this.etat_source = etat_source;

        JPanel centered = new JPanel();
        etiquette_transition.setPreferredSize(new Dimension(150, 30));

        GridLayout g = new GridLayout(3,2);
        g.setHgap(15);
        g.setVgap(15);

        getPan().add(liste_label);
        getPan().add(liste_etats);
        getPan().add(etiquette_label);
        getPan().add(etiquette_transition);

        this.valider.addActionListener(this);
        getPan().add(valider);

        this.add(getPan(), g);

    }

    public void actionPerformed(ActionEvent arg0){
        EnumEtat type_etat = ((EtatGraph) Ihm.instance().getEdGraphique().getElement_from_liste(etat_source)).getType();
        if(type_etat == EnumEtat.INIT)
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(),etat_source,(String)liste_etats.getSelectedItem());
        else if(type_etat == EnumEtat.SIMPLE)
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(), etat_source, (String) liste_etats.getSelectedItem());
        else
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(), etat_source, (String) liste_etats.getSelectedItem());
        this.dispose();
    }
}
