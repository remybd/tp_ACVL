package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by rémy on 06/11/2015.
 */
public class EditionTransition extends FenetrePopup implements ActionListener{

    private JComboBox nouvel_etat_source;
    private JComboBox liste_etats_destination;

    private JLabel etiquette_label = new JLabel("Nouvelle etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    private TransitionGraph transition_graph;
    private ArrayList<EtatGraph> liste_elements;

    public EditionTransition(TransitionGraph transition_graph){
        super();
        this.transition_graph = transition_graph;

        etiquette_transition.setPreferredSize(new Dimension(150, 30));
        etiquette_transition.setText(transition_graph.getNom());

        JPanel centered = new JPanel();
        centered.add(etiquette_label);
        centered.add(etiquette_transition);

        this.valider.addActionListener(this);

        centered.add(valider);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.add(getPan(), BorderLayout.CENTER);
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        EnumTransition type = transition_graph.getType();
        try {
            //Ihm.instance().getControleur().modifierTransition(transition_graph, liste_elements.get(index_source), liste_elements.get(index_destination), this.etiquette_transition.getText());
            ArrayList<EtatGraph> etats_source_destination = Ihm.instance().getControleur().getSourceAndDestination(transition_graph);
            Ihm.instance().getControleur().supprimerElement(transition_graph);
            Ihm.instance().getControleur().ajouterTransition(type, this.etiquette_transition.getText(),
                    etats_source_destination.get(0),etats_source_destination.get(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }

}
