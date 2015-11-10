package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 08/11/2015.
 */
public class MenuContextuelItemListener{

    //private static String nom_element;
    private static ElementGraphique element;

    public MenuContextuelItemListener(ElementGraphique element){
        this.element = element;
    }

    public static class CreationEtatSimpleListener implements ActionListener {

        public CreationEtatSimpleListener(){

        }
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(element, EnumEtat.SIMPLE);
        }
    }

    public static class CreationEtatCompositeListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(element, EnumEtat.COMPOSITE);
        }
    }

    public static class CreationEtatFinalListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(element, EnumEtat.FINAL);
        }
    }

    public static class ModifierEtatListener extends MenuContextuelItemListener implements ActionListener{

        public ModifierEtatListener(ElementGraphique element) {
            super(element);
        }

        public void actionPerformed(ActionEvent e) {
            new EditionEtat((EtatGraph)super.element);
        }
    }

    public static class AjouterTransitionListener extends MenuContextuelItemListener implements ActionListener{

        public AjouterTransitionListener(ElementGraphique element) {
            super(element);
        }

        public void actionPerformed(ActionEvent e) {
            if(((EtatGraph)element).getType() == EnumEtat.INIT)
                new CreationTransition((EtatGraph)element, EnumTransition.INIT);
            else if(((EtatGraph)element).getType() == EnumEtat.FINAL) {
                JOptionPane message_erreur = new JOptionPane();
                message_erreur.showMessageDialog(null, "Impossible d'ajouter une transition à un état pseudo-final", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
                new CreationTransition((EtatGraph)element, EnumTransition.INTER);
        }
    }

    public static class ModifierTransitionListener extends MenuContextuelItemListener implements ActionListener{

        public ModifierTransitionListener(ElementGraphique element) {
            super(element);
        }

        public void actionPerformed(ActionEvent e) {
            new EditionTransition((TransitionGraph)super.element);
        }
    }

    public static class ChoixConteneurListener extends MenuContextuelItemListener implements ActionListener{

        public ChoixConteneurListener(ElementGraphique element) {
            super(element);
        }

        public void actionPerformed(ActionEvent e) {
            new ChoixConteneur();
        }
    }

}
