package Vues;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationTransition extends FenetrePopup implements ActionListener{

    private JComboBox liste_etats = new JComboBox();
    private JLabel liste_label = new JLabel("Liste des etats de destination");
    private JLabel etiquette_label = new JLabel("Etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    public CreationTransition(){
        super();
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
        //Ihm.instance().getControleur().create_transition(, this.type);
        this.dispose();
    }
}
