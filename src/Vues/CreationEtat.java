package Vues;

import ElementsDiagramme.EnumEtat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationEtat extends FenetrePopup implements ActionListener{

    private JTextField text_etat = new JTextField();
    private JLabel nom_etat = new JLabel("Nom de l'etat");
    private JButton valider = new JButton("Valider");

    private ElementGraphique element_graphique;
    private EnumEtat type;

    public CreationEtat(ElementGraphique element_graphique, EnumEtat type){
        super();
        this.type = type;
        this.element_graphique = element_graphique;
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
        //Ihm.instance().getControleur().ajouterEtat(this.type, text_etat.getText(), (EtatGraph)this.element_graphique);
        String nom_etat = text_etat.getText();
        //TODO Test, à changer
        if(nom_etat.isEmpty()){
            JOptionPane message_erreur = new JOptionPane();
            message_erreur.showMessageDialog(null, "Un etat doit avoir un nom", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            //Ihm.instance().getEdGraphique().ajouterEtatSimple(text_etat.getText(), this.type);
            Ihm.instance().getControleur().ajouterEtat(this.type, text_etat.getText(), (EtatGraph)this.element_graphique);
            this.dispose();
        }
    }
}
