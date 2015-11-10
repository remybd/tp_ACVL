package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionInitiale extends Transition {
	private PseudoInitial _etatInit;
	private EtatIntermediaire _etatDest;
	
	public TransitionInitiale(Conteneur parent, PseudoInitial etatInitial, EtatIntermediaire _etatDest){
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
	


	@Override
	public boolean isTransitionFinale() {
		return false;
	}

	@Override
	public boolean isTransitionInitiale() {
		return true;
	}

	@Override
	public boolean isTransitionIntermediaire() {
		return false;
	}


	@Override
	public void setEtatSource(Etat etat) {
		this._etatInit = etat;
	}

	@Override
	public void setEtatDest(Etat etat) {
		this._etatDest = etat;		
	}

	@Override
	public Etat getEtatSource() {
		return _etatInit;
	}

	@Override
	public Etat getEtatDest() {
		return _etatDest;
	}
}
