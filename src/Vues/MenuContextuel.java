package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import com.mxgraph.model.mxCell;

import javax.swing.*;


/**
 * Created by Jerem on 03/11/2015.
 */
public class MenuContextuel extends JPopupMenu {

    private Ihm ihm = Ihm.instance();

    private JMenu ajouter = new JMenu("Ajouter");
    private JMenu ajouter_etat = new JMenu("Etat");
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

        EnumEtat type_etat = null;
        EnumTransition type_transition = null;

        if (element instanceof EtatGraph) {
            type_etat = ((EtatGraph) element).getType();
        } else if (element instanceof  TransitionGraph){
            type_transition = ((TransitionGraph) element).getType();
        }

        if (type_etat != null){

            modifier_transition.setEnabled(false);
            if (type_etat != EnumEtat.COMPOSITE) {
                ajouter_etat.setEnabled(false);
            }

        } else if (type_transition != null){

            ajouter.setEnabled(false);
            modifier_etat.setEnabled(false);
            modifier_conteneur.setEnabled(false);
            if(type_transition == EnumTransition.INIT)
                modifier_transition.setEnabled(false);

            // Aucun élément sélectionné
        } else {

            ajouter_transition.setEnabled(false);
            modifier.setEnabled(false);
            supprimer.setEnabled(false);
            modifier_conteneur.setEnabled(false);

        }

    }

    private void creationMenu(ElementGraphique element) {
        etat_simple.addActionListener(new MenuContextuelItemListener.CreationEtatSimpleListener());

        etat_final.addActionListener(new MenuContextuelItemListener.CreationEtatFinalListener());

        etat_composite.addActionListener(new MenuContextuelItemListener.CreationEtatCompositeListener());
        ajouter_etat.add(etat_simple);
        ajouter_etat.add(etat_final);
        ajouter_etat.add(etat_composite);
        ajouter.add(ajouter_etat);

        ajouter_transition.addActionListener(new MenuContextuelItemListener.AjouterTransitionListener(element));
        ajouter.add(ajouter_transition);
        add(ajouter);

        if (element != null) {
            modifier_etat.addActionListener(new MenuContextuelItemListener.ModifierEtatListener(element));
            modifier_transition.addActionListener(new MenuContextuelItemListener.ModifierTransitionListener(element));
        }
        modifier.add(modifier_etat);

        modifier.add(modifier_transition);

        add(modifier);

        supprimer.addActionListener(new MenuContextuelItemListener.SupprimerElementListener(element));
        add(supprimer);

    }
}
