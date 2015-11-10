package Vues;

import Exceptions.NameNotModifiableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rémy on 06/11/2015.
 */
public class EditionEtat extends FenetrePopup implements ActionListener{
    private JTextField text_etat = new JTextField();
    private JLabel nom_etat = new JLabel("Nouveau nom");
    private JButton valider = new JButton("Valider");

    EnumEtat type;

    public EditionEtat(EtatGraph etat_graph){
        super();
        //this.type = getParent();
        System.out.println();

        JPanel centered = new JPanel();
        text_etat.setPreferredSize(new Dimension(150,30));
        text_etat.setText(etat_graph.getNom());
        centered.add(nom_etat);
        centered.add(text_etat);

        valider.addActionListener(this);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.add(getPan(), BorderLayout.CENTER);
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        ElementGraphique etat_courant = Ihm.instance().getEdGraphique().getElement_from_liste(text_etat.getText());
        try{
            Ihm.instance().getControleur().renommerEtat((EtatGraph)etat_courant, this.type);
        } catch(NameNotModifiableException e){
            //TODO Voir les traitements à effectuer
            e.printStackTrace();
        }
        this.dispose();
    }
}
