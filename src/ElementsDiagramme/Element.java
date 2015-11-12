package ElementsDiagramme;

import java.util.ArrayList;

/**
 * Created by r�my on 04/11/2015.
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

    public void setConteneurParent(Conteneur conteneurParent) {
        this.conteneurParent = conteneurParent;
    }

    public abstract ArrayList<Element> supprimer(); //suppression de l'�l�ment this

    public abstract boolean isEtat();
    public abstract boolean isEtatIntermediaire();
    public abstract boolean isEtatComposite();
    public abstract boolean isEtatSimple();
    public abstract boolean isEtatPseudoInitial();
    public abstract boolean isEtatPseudoFinal();
    
    public abstract boolean isTransition();
    public abstract boolean isTransitionFinale();
    public abstract boolean isTransitionInitiale();
    public abstract boolean isTransitionIntermediaire();
}
