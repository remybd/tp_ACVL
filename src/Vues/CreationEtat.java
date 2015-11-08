package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleurs.ControleurDiagramme;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationEtat extends FenetrePopup implements ActionListener{

    private JTextField text_etat = new JTextField();
    private JLabel nom_etat = new JLabel("Nom de l'etat");
    private JButton valider = new JButton("Valider");

    EnumEtat type;

    public CreationEtat(EnumEtat type){
        super();
        this.type = type;
        JPanel centered = new JPanel();
        text_etat.setPreferredSize(new Dimension(150,30));
        centered.add(nom_etat);
        centered.add(text_etat);

        valider.addActionListener(this);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.add(getPan(), BorderLayout.CENTER);
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        //Ihm.instance().getControleur().create_etat(text_etat.getText(), this.type);
        this.dispose();
    }
}
