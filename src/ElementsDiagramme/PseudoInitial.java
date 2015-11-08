package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoInitial extends Etat {
	private TransitionInitiale _trans;

	public PseudoInitial(ObservateurVue obs, String nom){
		super(obs, nom);
	}
	
	public PseudoInitial(ObservateurVue obs, String nom, TransitionInitiale transition){
		super(obs, nom);
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

	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isTransition() {
		return false;
	}
}
