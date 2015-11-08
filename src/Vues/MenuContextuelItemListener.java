package Vues;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 08/11/2015.
 */
public class MenuContextuelItemListener{

    private static String nom_element;

    public MenuContextuelItemListener(String nom_element){
        this.nom_element = nom_element;
    }

    public static class CreationEtatSimpleListener implements ActionListener {

        public CreationEtatSimpleListener(){

        }
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(EnumEtat.SIMPLE);
        }
    }

    public static class CreationEtatCompositeListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(EnumEtat.COMPOSITE);
        }
    }

    public static class CreationEtatFinalListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new CreationEtat(EnumEtat.FINAL);
        }
    }

    public static class ModifierEtatListener extends MenuContextuelItemListener implements ActionListener{

        public ModifierEtatListener(String nom_element) {
            super(nom_element);
        }

        public void actionPerformed(ActionEvent e) {
            new EditionEtat(super.nom_element);
        }
    }

    public static class AjouterTransitionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new CreationTransition();
        }
    }

    public static class ModifierTransitionListener extends MenuContextuelItemListener implements ActionListener{

        public ModifierTransitionListener(String nom_element) {
            super(nom_element);
        }

        public void actionPerformed(ActionEvent e) {
            new EditionTransition(super.nom_element);
        }
    }

    public static class ChoixConteneurListener extends MenuContextuelItemListener implements ActionListener{

        public ChoixConteneurListener(String nom_element) {
            super(nom_element);
        }

        public void actionPerformed(ActionEvent e) {
            new ChoixConteneur();
        }
    }

}
