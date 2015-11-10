package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionInitiale extends Transition {
	private PseudoInitial _etatInit;
	
	public TransitionInitiale(Conteneur parent, PseudoInitial etatInitial){
		super(parent);
		this.setPseudoInitial(etatInitial);
	}

	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}

	public void setPseudoInitial(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
	}

	@Override
	public void supprimer() {
		if(_etatInit == null)
			return;
		
		_etatInit.setTransition(null);
	}
	
}
