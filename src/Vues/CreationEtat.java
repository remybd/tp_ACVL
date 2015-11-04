package Vues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 03/11/2015.
 */
public class CreationEtat extends FenetrePopup {

    private JTextField text_etat = new JTextField();
    private JLabel nom_etat = new JLabel("Nom de l'etat");
    private JButton valider = new JButton("Valider");

    public CreationEtat(){
        JPanel centered = new JPanel();
        text_etat.setPreferredSize(new Dimension(150,30));
        centered.add(nom_etat);
        centered.add(text_etat);

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDefaultCloseOperation();
            }
        });
        centered.add(valider);
        getPan().add(centered, BorderLayout.CENTER);
        this.setContentPane(getPan());
    }
}
