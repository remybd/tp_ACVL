package Controleurs;

import ElementsDiagramme.Conteneur;
import ElementsDiagramme.Element;
import Erreurs.Erreur;
import Vues.ElementGraphique;
import Vues.EtatGraph;
import Vues.TransitionGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by rémy on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private HashMap<ElementGraphique,Element> correspondance;

    public ControleurDiagramme(Conteneur mainConteneur){
        this.mainConteneur = mainConteneur;
    }

    public void ajouterTransition(TransitionGraph t){

    }

    public void ajouterEtat(EtatGraph e){

    }

    public void renommerEtat(EtatGraph eg, String nom){

    }

    public void modifierConteneurParent(EtatGraph eg, EtatGraph parent){

    }

    public void supprimerElement(ElementGraphique e){

    }

    public void changerSource(TransitionGraph t, EtatGraph source){

    }

    public void changerDest(TransitionGraph t, EtatGraph dest){

    }

    public void modifierEtiquette(TransitionGraph t){

    }

    public HashSet<Erreur> chercherErreurs(){

        return new HashSet<Erreur>();
    }

    public HashMap<ElementGraphique, Element> getCorrespondance() {
        return correspondance;
    }

    public Conteneur getMainConteneur() {
        return mainConteneur;
    }
}
