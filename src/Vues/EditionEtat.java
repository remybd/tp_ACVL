package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ElementsDiagramme.EnumEtat;
import Exceptions.NameNotModifiableException;

/**
 * Created by r�my on 06/11/2015.
 */
public class EditionEtat extends FenetrePopup implements ActionListener{
    private JTextField text_etat = new JTextField();
    private JLabel nom_etat = new JLabel("Nouveau nom");
    private JButton valider = new JButton("Valider");

    private EtatGraph etat_courant;
    private EnumEtat type;

    public EditionEtat(EtatGraph etat_courant){
        super();
        this.etat_courant = etat_courant;
        this.type = etat_courant.getType();

        JPanel centered = new JPanel();
        text_etat.setPreferredSize(new Dimension(150,30));
        text_etat.setText(etat_courant.getNom());
        centered.add(nom_etat);
        centered.add(text_etat);

        valider.addActionListener(this);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.add(getPan(), BorderLayout.CENTER);
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        try{
            Ihm.instance().getControleur().renommerEtat(etat_courant, text_etat.getText());
        } catch(NameNotModifiableException e){
            e.printStackTrace();
        }
        this.dispose();
    }
}
