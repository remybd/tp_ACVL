package Vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 08/11/2015.
 */
public class ChoixConteneur extends FenetrePopup implements ActionListener{

    private JComboBox liste_conteneurs = new JComboBox();
    private JLabel label_liste = new JLabel("Liste des conteneurs pour l'etat " + "");
    private JButton valider = new JButton("Valider");

    EnumEtat type;

    public ChoixConteneur(){
        super();
        this.type = type;
        JPanel centered = new JPanel();
        centered.add(label_liste, BorderLayout.NORTH);
        centered.add(liste_conteneurs, BorderLayout.CENTER);

        valider.addActionListener(this);
        centered.add(valider, BorderLayout.SOUTH);

        getPan().add(centered);
        this.add(getPan());
        //this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        //Ihm.instance().getControleur().modifier_etat(text_etat.getText(), this.type);
        this.dispose();
    }
}
