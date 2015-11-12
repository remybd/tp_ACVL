package Erreurs;

import ElementsDiagramme.Transition;
import Vues.ObservateurVue;

/**
 * Created by r�my on 05/11/2015.
 */
public class TransitionNonDeterministe extends Erreur{
    private Transition transitionParent;

    public TransitionNonDeterministe(Transition transition, int importance, ObservateurVue zoneErreur){
        super("Transition Non Deterministe", importance, zoneErreur);
        System.out.println("Details : transition problematique : "+transition.getEtiquette());
        
        this.transitionParent = transition;
    }

    public Transition getTransitionParent() {
        return transitionParent;
    }

	@Override
	public String getMessage() {
		return this.getNom()+" - Plusieurs transitions d'un meme etat source ont le meme [evenement]/action : "+this.getTransitionParent().getEtiquette();
	}
}
