package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionInitiale extends Transition {
	private PseudoInitial _etatInit;
	
	public TransitionInitiale(Observateur obs, PseudoInitial etatInitial){
		super(obs);
		this.setPseudoInitial(etatInitial);
	}

	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}

	public void setPseudoInitial(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
	}	
	
}
