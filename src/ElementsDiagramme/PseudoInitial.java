package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoInitial extends Etat {
	private TransitionInitiale _trans;

	public PseudoInitial(Conteneur parent, String nom){
		super(parent, nom);
	}
	
	public PseudoInitial(Conteneur parent, String nom, TransitionInitiale transition){
		super(parent, nom);
		this.setTransition(transition);
	}
	
	public TransitionInitiale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionInitiale _trans) {
		this._trans = _trans;
	}
	

	@Override
	public void supprimer() {
		if(_trans == null)
			return;
		
		_trans.supprimer();	
	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isTransition() {
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
		return true;
	}

	@Override
	public boolean isEtatPseudoFinal() {
		return false;
	}
}
