package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoFinal  extends Etat {
	private TransitionFinale _trans;

	public PseudoFinal(String nom, TransitionFinale transitionFinale){
		super(nom);
		this.setTransition(transitionFinale);
	}
	
	public TransitionFinale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionFinale _trans) {
		this._trans = _trans;
	}

	@Override
	public void attache(Observateur observateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detache(Observateur observateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informe() {
		// TODO Auto-generated method stub
		
	}
	
}
