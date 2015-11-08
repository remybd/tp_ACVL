package Vues;

import com.mxgraph.model.mxCell;

import javax.swing.*;


/**
 * Created by Jerem on 03/11/2015.
 */
public class MenuContextuel extends JPopupMenu {

    private Ihm ihm = Ihm.instance();

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

    private mxCell child;

    public MenuContextuel(EnumObjetSelectionne objSelectionne, ElementGraphique element) {

        creationMenu(element);

        if (objSelectionne.equals(EnumObjetSelectionne.AUCUN)) {
            ajouter_transition.setEnabled(false);
            modifier.setEnabled(false);
            supprimer.setEnabled(false);
            modifier_conteneur.setEnabled(false);
        } else if (objSelectionne.equals(EnumObjetSelectionne.ETAT)) {
            modifier_transition.setEnabled(false);
        } else if (objSelectionne.equals(EnumObjetSelectionne.TRANSITION)) {
            ajouter.setEnabled(false);
            modifier_etat.setEnabled(false);
            modifier_conteneur.setEnabled(false);
        }
    }

    private void creationMenu(ElementGraphique element) {
        etat_simple.addActionListener(new MenuContextuelItemListener.CreationEtatSimpleListener());

        etat_final.addActionListener(new MenuContextuelItemListener.CreationEtatFinalListener());

        etat_composite.addActionListener(new MenuContextuelItemListener.CreationEtatCompositeListener());
        etat.add(etat_simple);
        etat.add(etat_final);
        etat.add(etat_composite);
        ajouter.add(etat);

        ajouter_transition.addActionListener(new MenuContextuelItemListener.AjouterTransitionListener());
        ajouter.add(ajouter_transition);
        add(ajouter);

        if (element != null) {
            modifier_etat.addActionListener(new MenuContextuelItemListener.ModifierEtatListener(element.getNom()));
            modifier_transition.addActionListener(new MenuContextuelItemListener.ModifierTransitionListener(element.getNom()));
        }
        modifier.add(modifier_etat);

        modifier.add(modifier_transition);

        add(modifier);
        add(supprimer);

        modifier_conteneur.addActionListener(new MenuContextuelItemListener.ChoixConteneurListener(""));
        add(modifier_conteneur);

    }
}
