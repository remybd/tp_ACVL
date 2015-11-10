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

		//TO DO : peut �tre modifier les constructeurs pour ne pas avoir � mettre null pour l'Observateur
		if(type == EnumTransition.INTER){
			if(!s.isEtatIntermediaire() || !d.isEtatIntermediaire())
				throw new NoIntermediaryStateException();

			t = new TransitionIntermediaire(parent,etiquette,(EtatIntermediaire)s,(EtatIntermediaire)d);
		}
		else if(type == EnumTransition.FINAL){
			if(!s.isEtatIntermediaire() || !(d instanceof PseudoFinal))
				throw new NoIntermediaryAndFinalStateException();
			//TO DO : peut �tre modifier le constructeur pour avoir � remplir les �tats
			t = new TransitionFinale(parent,etiquette);
		}
		else{//transition initiale
			if(!(s instanceof PseudoInitial) || !d.isEtatIntermediaire())
				throw new NoIntermediaryAndInitialStateException();
			//TO DO : peut �tre modifier le constructeur pour avoir � remplir l'�tat de destination ?
			t = new TransitionInitiale(parent,(PseudoInitial)s);
		}

		return t;
	}
	
	public abstract void setEtatSource(Etat etat);
	public abstract void setEtatDest(Etat etat);

	public abstract Etat getEtatSource();
	public abstract Etat getEtatDest();
	
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
