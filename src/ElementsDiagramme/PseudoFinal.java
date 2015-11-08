package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoFinal  extends Etat {
	private TransitionFinale _trans;

	public PseudoFinal(ObservateurVue obs, String nom, TransitionFinale transitionFinale){
		super(obs, nom);
		this.setTransition(transitionFinale);
	}
	
	public TransitionFinale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionFinale _trans) {
		this._trans = _trans;
	}

	@Override
	public void supprimer() {

	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}
}
