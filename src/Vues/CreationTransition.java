package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import ElementsDiagramme.TransitionIntermediaire;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationTransition extends FenetrePopup implements ActionListener{

    private JComboBox liste_etats;

    private JLabel liste_label = new JLabel("Liste des etats de destination");

    private JLabel evenement_label = new JLabel("Evenement");
    private JTextField evenement_transition = new JTextField();

    private JLabel garde_label = new JLabel("Garde");
    private JTextField garde_transition = new JTextField();

    private JLabel action_label = new JLabel("Action");
    private JTextField action_transition = new JTextField();

    private JButton valider = new JButton("Valider");

    private EnumTransition type;
    private EtatGraph etat_source;

    private ArrayList<EtatGraph> liste_elements;

    public CreationTransition(EtatGraph etat_source, EnumTransition type) throws Exception {
        super();
        this.type = type;
        this.etat_source = etat_source;

        liste_elements = Ihm.instance().getControleur().getStatesFromSameConteneur(etat_source);
        String[] liste_noms_etats = new String[liste_elements.size()];

        int i=0;
        for(EtatGraph e: liste_elements){
            liste_noms_etats[i] = e.getNom();
            i++;
        }

        liste_etats = new JComboBox(liste_noms_etats);

        evenement_transition.setPreferredSize(new Dimension(150, 30));
        garde_transition.setPreferredSize(new Dimension(150, 30));
        action_transition.setPreferredSize(new Dimension(150, 30));

        JPanel centered = new JPanel();
        centered.add(liste_label);
        centered.add(liste_etats);
        centered.add(evenement_label);
        centered.add(evenement_transition);
        centered.add(garde_label);
        centered.add(garde_transition);
        centered.add(action_label);
        centered.add(action_transition);

        this.valider.addActionListener(this);

        centered.add(valider);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.add(getPan(), BorderLayout.CENTER);
        this.pack();

    }

    public void actionPerformed(ActionEvent arg0){
        int index = liste_etats.getSelectedIndex();
        EnumEtat type_etat = liste_elements.get(index).getType();

        String etiquette = this.evenement_transition.getText() + " [" + this.garde_transition.getText() + "] /" + this.action_transition.getText();
        try {
            if(type_etat == EnumEtat.FINAL)
                Ihm.instance().getControleur().ajouterTransition(EnumTransition.FINAL, etiquette,etat_source, liste_elements.get(index));
            else
                Ihm.instance().getControleur().ajouterTransition(this.type, etiquette,etat_source, liste_elements.get(index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }
}
