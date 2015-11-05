package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoInitial extends Etat {
	private TransitionInitiale _trans;

	public PseudoInitial(Observateur obs, String nom){
		super(obs, nom);
	}
	
	public PseudoInitial(Observateur obs, String nom, TransitionInitiale transition){
		super(obs, nom);
		this.setTransition(transition);
	}
	
	public TransitionInitiale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionInitiale _trans) {
		this._trans = _trans;
	}	
}
