package ElementsDiagramme;

import Exceptions.NoIntermediaryAndFinalStateException;
import Exceptions.NoIntermediaryAndInitialStateException;
import Exceptions.NoIntermediaryStateException;

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

		if(type == EnumTransition.INTER){
			if(!s.isEtatIntermediaire() || !d.isEtatIntermediaire())
				throw new NoIntermediaryStateException();

			t = new TransitionIntermediaire(parent,etiquette,(EtatIntermediaire)s,(EtatIntermediaire)d);
			((EtatIntermediaire) s).addDestination(t);
			((EtatIntermediaire) d).addSource(t);
		}
		else if(type == EnumTransition.FINAL){
			if(!s.isEtatIntermediaire() || !d.isEtatPseudoFinal())
				throw new NoIntermediaryAndFinalStateException();

			t = new TransitionFinale(parent,etiquette,(EtatIntermediaire)s,(PseudoFinal)d);
			((EtatIntermediaire) s).addDestination(t);
			((PseudoFinal) d).addTransition((TransitionFinale)t);
		}
		else{//transition initiale
			if(!(s.isEtatPseudoInitial()) || !d.isEtatIntermediaire())
				throw new NoIntermediaryAndInitialStateException();
			t = new TransitionInitiale(parent,(PseudoInitial)s, (EtatIntermediaire)d);
			((PseudoInitial) s).setTransition((TransitionInitiale)t);
			((EtatIntermediaire) d).addSource(t);
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

	public abstract Etat getEtatSource();

	public abstract Etat getEtatDestination();

	public abstract String getEtiquette();

	public abstract String getEvt();

	public abstract String getGarde();

	public abstract String getAction();
}