package Erreurs;

import ElementsDiagramme.Sujet;
import ElementsDiagramme.Transition;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class TransitionNonDeterministe extends Erreur{
    private Transition transitionParent;

    public TransitionNonDeterministe(Transition transition, int importance){
        super("Transition Non Déterministe", importance);
        this.transitionParent = transition;
    }

    public Transition getTransitionParent() {
        return transitionParent;
    }
}
