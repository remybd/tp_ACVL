package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import ElementsDiagramme.Etat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JPanel line_1 = new JPanel();
    private JPanel line_2 = new JPanel();
    private JPanel line_3 = new JPanel();

    private EnumTransition type;
    private EtatGraph etat_source;

    private ArrayList<EtatGraph> liste_elements;

    public CreationTransition(EtatGraph etat_source, EnumTransition type) throws Exception {
        super();
        this.type = type;
        this.etat_source = etat_source;

        //TODO On ne récupère pas la bonne liste !!!! (Pas d'état composite par exemple)
        liste_elements = Ihm.instance().getControleur().getStatesFromSameConteneur(etat_source);
        String[] liste_noms_etats = new String[liste_elements.size()];

        int i=0;
        for(EtatGraph e: liste_elements){
            liste_noms_etats[i] = e.getNom();
            i++;
        }

        etiquette_transition.setPreferredSize(new Dimension(150, 30));

        liste_etats = new JComboBox(liste_noms_etats);

        line_2.setLayout(new BoxLayout(line_2, BoxLayout.LINE_AXIS));
        line_2.add(liste_label);
        line_2.add(liste_etats);

        line_3.setLayout(new BoxLayout(line_3, BoxLayout.LINE_AXIS));
        line_3.add(etiquette_label);
        if(etat_source.getType() != EnumEtat.INIT)
            line_3.add(etiquette_transition);
        this.valider.addActionListener(this);

        getPan().setLayout(new BoxLayout(getPan(), BoxLayout.PAGE_AXIS));
        getPan().add(line_1);
        getPan().add(line_2);
        getPan().add(line_3);
        getPan().add(valider);

        this.add(getPan());
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        EnumEtat type_etat = etat_source.getType();
        int index = liste_etats.getSelectedIndex();
        try {
            Ihm.instance().getControleur().ajouterTransition(this.type, this.etiquette_transition.getText(),etat_source, liste_elements.get(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }
}
