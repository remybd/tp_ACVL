package Vues;

import ElementsDiagramme.EnumEtat;

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
    private JLabel liste_label_source = new JLabel("Liste des etats source");
    private JLabel liste_label_destination = new JLabel("Liste des etats de destination");

    private JLabel etiquette_label = new JLabel("Etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    private JPanel line_1 = new JPanel();
    private JPanel line_2 = new JPanel();
    private JPanel line_3 = new JPanel();
    private JPanel line_4 = new JPanel();

    private TransitionGraph transition_graph;
    private ArrayList<EtatGraph> liste_elements;

    public EditionTransition(TransitionGraph transition_graph){
        super();
        this.transition_graph = transition_graph;

        try {
            liste_elements = Ihm.instance().getControleur().getStatesFromSameConteneur(transition_graph);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] noms_etats_source = new String[liste_elements.size()];
        String[] noms_etats_destination = new String[liste_elements.size()];


        int i=0;
        for(EtatGraph e: liste_elements){
            if(e.getType() != EnumEtat.FINAL)
                noms_etats_source[i] = e.getNom();
            if(e.getType() != EnumEtat.INIT)
                noms_etats_destination[i] = e.getNom();
            i++;
        }

        nouvel_etat_source = new JComboBox(noms_etats_source);
        liste_etats_destination = new JComboBox(noms_etats_destination);

        etiquette_transition.setPreferredSize(new Dimension(150, 30));
        etiquette_transition.setText(transition_graph.getNom());

        line_1.setLayout(new BoxLayout(line_1, BoxLayout.LINE_AXIS));
        line_1.add(liste_label_source);
        line_1.add(nouvel_etat_source);

        line_2.setLayout(new BoxLayout(line_2, BoxLayout.LINE_AXIS));
        line_2.add(liste_label_destination);
        line_2.add(liste_etats_destination);

        line_3.setLayout(new BoxLayout(line_3, BoxLayout.LINE_AXIS));
        line_3.add(etiquette_label);
        etiquette_transition.setText(transition_graph.getNom());
        line_3.add(etiquette_transition);
        this.valider.addActionListener(this);

        line_4.setLayout(new BoxLayout(line_4, BoxLayout.LINE_AXIS));
        line_4.add(valider);

        getPan().setLayout(new BoxLayout(getPan(), BoxLayout.PAGE_AXIS));
        getPan().add(line_1);
        getPan().add(line_2);
        getPan().add(line_3);
        getPan().add(line_4);

        this.add(getPan());
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        int index_source = nouvel_etat_source.getSelectedIndex();
        int index_destination = nouvel_etat_source.getSelectedIndex();

        try {
            Ihm.instance().getControleur().modifierTransition(transition_graph, liste_elements.get(index_source), liste_elements.get(index_destination), this.etiquette_transition.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }

}
