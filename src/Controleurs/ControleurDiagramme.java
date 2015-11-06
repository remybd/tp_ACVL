package Controleurs;

import ElementsDiagramme.Conteneur;
import ElementsDiagramme.Element;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;
import Erreurs.Erreur;
import Vues.ElementGraphique;
import Vues.EtatGraph;
import Vues.Ihm;
import Vues.TransitionGraph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by rémy on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private Ihm ihm;
    private HashMap<ElementGraphique,Element> correspondance;

    public ControleurDiagramme(Conteneur mainConteneur, Ihm ihm){
        this.mainConteneur = mainConteneur;
        this.ihm = ihm;
    }

    public void ajouterTransition(Transition t){
        mainConteneur.addElmt(t);
        TransitionGraph tg = ihm.createTransitionGraph(t);
        correspondance.put(tg,t);
    }

    public void ajouterEtat(Etat e){
        mainConteneur.addElmt(e);
        EtatGraph eg = ihm.createEtatGraph(e);
        correspondance.put(eg,e);
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
