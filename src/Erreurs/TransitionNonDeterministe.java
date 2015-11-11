package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Sujet;
import ElementsDiagramme.Transition;

/**
 * Created by r�my on 05/11/2015.
 */
public class TransitionNonDeterministe extends Erreur{
    private Transition transitionParent;

    public TransitionNonDeterministe(Transition transition, int importance, ObservateurVue zoneErreur){
        super("Transition Non D�terministe", importance, zoneErreur);
        System.out.println("D�tails : transition probl�matique : "+transition.getEtiquette());
        
        this.transitionParent = transition;
    }

    public Transition getTransitionParent() {
        return transitionParent;
    }

	@Override
	public String getMessage() {
		return this.getNom()+" - Plusieurs transitions d'un m�me �tat source ont le m�me [�v�nement]/action : "+this.getTransitionParent().getEtiquette();
	}
}
