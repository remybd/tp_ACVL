package ElementsDiagramme;

import Exceptions.NoIntermediaryAndFinalStateException;
import Exceptions.NoIntermediaryAndInitialStateException;
import Exceptions.NoIntermediaryStateException;
import Vues.ObservateurVue;
import Vues.TransitionGraph;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Transition extends Element {

	public Transition(ObservateurVue obs){
		super(obs);
	}

	public static Transition creerTransition(EnumTransition type, String etiquette, Etat s, Etat d) throws Exception {
		Transition t;

		//TO DO : peut être modifier les constructeurs pour ne pas avoir à mettre null pour l'Observateur
		if(type == EnumTransition.INTER){
			if(!s.isEtatIntermediaire() || !d.isEtatIntermediaire())
				throw new NoIntermediaryStateException();

			t = new TransitionIntermediaire(null,etiquette,(EtatIntermediaire)s,(EtatIntermediaire)d);
		}
		else if(type == EnumTransition.FINAL){
			if(!s.isEtatIntermediaire() || !(d instanceof PseudoFinal))
				throw new NoIntermediaryAndFinalStateException();
			//TO DO : peut être modifier le constructeur pour avoir à remplir les états
			t = new TransitionFinale(null,etiquette);
		}
		else{//transition initiale
			if(!(s instanceof PseudoInitial) || !d.isEtatIntermediaire())
				throw new NoIntermediaryAndInitialStateException();
			//TO DO : peut être modifier le constructeur pour avoir à remplir l'état de destination ?
			t = new TransitionInitiale(null,(PseudoInitial)s);
		}

		return t;
	}
}
