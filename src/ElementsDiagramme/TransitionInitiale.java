package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionInitiale extends Transition {
	private PseudoInitial _etatInit;
	
	public TransitionInitiale(PseudoInitial etatInitial){
		this.setPseudoInitial(etatInitial);
	}

	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}

	public void setPseudoInitial(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
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
