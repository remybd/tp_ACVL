package Vues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 03/11/2015.
 */
public class MenuContextuel extends JPopupMenu {

    private JMenu ajouter = new JMenu("Ajouter");
    private JMenu etat = new JMenu("Etat");
    private JMenuItem etat_simple = new JMenuItem("Simple");
    private JMenuItem etat_final = new JMenuItem("Final");
    private JMenuItem etat_composite = new JMenuItem("Composite");

    private JMenuItem ajouter_transition = new JMenuItem("Transition");

    private JMenu modifier = new JMenu("Modifier");
    private JMenuItem modifier_etat = new JMenuItem("Etat");
    private JMenuItem modifier_transition = new JMenuItem("Transition");

    private JMenuItem supprimer = new JMenuItem("Supprimer");

    private JMenuItem modifier_conteneur = new JMenuItem("Modifier le conteneur parent");

    public MenuContextuel(){

        etat_simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreationEtat();
            }
        });

        etat_final.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreationEtat();
            }
        });

        etat_composite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreationEtat();
            }
        });
        etat.add(etat_simple);
        etat.add(etat_final);
        etat.add(etat_composite);
        ajouter.add(etat);

        ajouter_transition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreationTransition();
            }
        });
        ajouter.add(ajouter_transition);
        add(ajouter);

        modifier_etat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditionEtat();
            }
        });
        modifier.add(modifier_etat);

        modifier_transition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditionTransition();
            }
        });
        modifier.add(modifier_transition);

        add(modifier);
        add(supprimer);
        add(modifier_conteneur);

    }
}
