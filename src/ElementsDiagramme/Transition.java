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

	public Transition(Conteneur parent){
		super(parent);
	}

	public static Transition creerTransition(EnumTransition type, String etiquette, Etat s, Etat d, Conteneur parent) throws Exception {
		Transition t;

		//TO DO : peut être modifier les constructeurs pour ne pas avoir à mettre null pour l'Observateur
		if(type == EnumTransition.INTER){
			if(!s.isEtatIntermediaire() || !d.isEtatIntermediaire())
				throw new NoIntermediaryStateException();

			t = new TransitionIntermediaire(parent,etiquette,(EtatIntermediaire)s,(EtatIntermediaire)d);
		}
		else if(type == EnumTransition.FINAL){
			if(!s.isEtatIntermediaire() || !d.isEtatPseudoFinal())
				throw new NoIntermediaryAndFinalStateException();
			//TO DO : peut être modifier le constructeur pour avoir à remplir les états
			t = new TransitionFinale(parent,etiquette);
		}
		else{//transition initiale
			if(!(s.isEtatPseudoInitial()) || !d.isEtatIntermediaire())
				throw new NoIntermediaryAndInitialStateException();
			//TO DO : peut être modifier le constructeur pour avoir à remplir l'état de destination ?
			t = new TransitionInitiale(parent,(PseudoInitial)s, (EtatIntermediaire)d);
		}

		return t;
	}
	
	
	@Override
	public boolean isEtat(){
		return false;
	}

	@Override
	public boolean isTransition(){
		return true;
	}
	

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}


	@Override
	public boolean isEtatComposite() {
		return false;
	}

	@Override
	public boolean isEtatSimple() {
		return false;
	}

	@Override
	public boolean isEtatPseudoInitial() {
		return false;
	}

	@Override
	public boolean isEtatPseudoFinal() {
		return false;
	}
}
