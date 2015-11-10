package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 * Created by r�my on 04/11/2015.
 */
public abstract class Element extends Sujet{
    private Conteneur conteneurParent;

    public Element(ObservateurVue o, Conteneur c){
        super(o);
        this.conteneurParent = c;
    }

    public Conteneur getConteneurParent() {
        return conteneurParent;
    }

    public abstract void supprimer(); //suppression de l'�l�ment this
    public abstract boolean isEtatIntermediaire();
    public abstract boolean isTransition();
    public abstract boolean isEtatComposite();
    public abstract boolean isEtatSimple();
    public abstract boolean isEtatPseudoInitial();
    public abstract boolean isEtatPseudoFinal();
}
