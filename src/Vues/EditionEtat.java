package Vues;

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

    public EditionEtat(String Label){
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
        //Ihm.instance().getControleur().modifier_etat(text_etat.getText(), this.type);
        this.dispose();
    }
}
