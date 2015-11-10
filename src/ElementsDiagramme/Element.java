package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 * Created by rémy on 04/11/2015.
 */
public abstract class Element extends Sujet{
    private Conteneur conteneurParent;

    public Element(Conteneur c){
        super();
        this.conteneurParent = c;
    }

    public Conteneur getConteneurParent() {
        return conteneurParent;
    }

    public abstract void supprimer(); //suppression de l'élément this

    public abstract boolean isEtat();
    public abstract boolean isEtatIntermediaire();
    public abstract boolean isTransition();
    public abstract boolean isEtatComposite();
    public abstract boolean isEtatSimple();
    public abstract boolean isEtatPseudoInitial();
    public abstract boolean isEtatPseudoFinal();
}
