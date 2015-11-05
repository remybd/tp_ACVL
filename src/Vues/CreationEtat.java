package Vues;

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

    public CreationEtat(ControleurDiagramme controleur){
        super(controleur);
        JPanel centered = new JPanel();
        text_etat.setPreferredSize(new Dimension(150,30));
        centered.add(nom_etat);
        centered.add(text_etat);

        valider.addActionListener(this);

        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.setContentPane(getPan());
    }

    public JTextField getText_etat() {
        return text_etat;
    }

    public void setText_etat(JTextField text_etat) {
        this.text_etat = text_etat;
    }

    public JLabel getNom_etat() {
        return nom_etat;
    }

    public void setNom_etat(JLabel nom_etat) {
        this.nom_etat = nom_etat;
    }

    public JButton getValider() {
        return valider;
    }

    public void setValider(JButton valider) {
        this.valider = valider;
    }

    public void actionPerformed(ActionEvent arg0){
        //controlleurDiagramme.create_etat(text_etat.getText());
    }
}
