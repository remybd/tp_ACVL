package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Sujet;
import ElementsDiagramme.Transition;

/**
 * Created by rémy on 05/11/2015.
 */
public class TransitionNonDeterministe extends Erreur{
    private Transition transitionParent;

    public TransitionNonDeterministe(Transition transition, int importance, ObservateurVue zoneErreur){
        super("Transition Non Déterministe", importance, zoneErreur);
        System.out.println("Détails : transition problématique : "+transition.getEtiquette());
        
        this.transitionParent = transition;
    }

    public Transition getTransitionParent() {
        return transitionParent;
    }

	@Override
	public String getMessage() {
		return this.getNom()+" - Plusieurs transitions d'un même état source ont le même [événement]/action : "+this.getTransitionParent().getEtiquette();
	}
}
