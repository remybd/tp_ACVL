package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ElementsDiagramme.EnumTransition;
import ElementsDiagramme.TransitionIntermediaire;

/**
 * Created by r�my on 06/11/2015.
 */
public class EditionTransition extends FenetrePopup implements ActionListener{

    private JLabel evenement_label = new JLabel("Evenement");
    private JTextField evenement_transition = new JTextField();

    private JLabel garde_label = new JLabel("Garde");
    private JTextField garde_transition = new JTextField();

    private JLabel action_label = new JLabel("Action");
    private JTextField action_transition = new JTextField();

    private JButton valider = new JButton("Valider");

    private TransitionGraph transition_graph;
    private ArrayList<EtatGraph> liste_elements;

    public EditionTransition(TransitionGraph transition_graph){
        super();
        this.transition_graph = transition_graph;

        evenement_transition.setPreferredSize(new Dimension(150, 30));
        garde_transition.setPreferredSize(new Dimension(150, 30));
        action_transition.setPreferredSize(new Dimension(150, 30));

        String evenement = TransitionIntermediaire.getEvt(transition_graph.getNom());
        String garde = TransitionIntermediaire.getGarde(transition_graph.getNom());
        String action = TransitionIntermediaire.getAction(transition_graph.getNom());

        if(!evenement.isEmpty())
            evenement_transition.setText(evenement);
        if(!garde.isEmpty())
            garde_transition.setText(garde);
        if(!action.isEmpty())
            action_transition.setText(action);

        JPanel centered = new JPanel();
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
        EnumTransition type = transition_graph.getType();
        String etiquette = evenement_transition.getText() + " [" + garde_transition.getText() + "] / " + action_transition.getText();
        try {
            ArrayList<EtatGraph> etats_source_destination = Ihm.instance().getControleur().getSourceAndDestination(transition_graph);
            Ihm.instance().getControleur().supprimerElement(transition_graph);
            Ihm.instance().getControleur().ajouterTransition(type, etiquette,
                    etats_source_destination.get(0),etats_source_destination.get(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }

}
