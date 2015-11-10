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

	public TransitionInitiale(TransitionIntermediaire trans, PseudoInitial pseudoInitial) {
		super(trans.getConteneurParent());
		this.setObservateur(trans.getObservateur());
		
		this.setPseudoInitial(pseudoInitial);
		this.setEtatDest(trans.getEtatDest());
	}
	
	public TransitionInitiale(TransitionFinale trans, PseudoInitial pseudoInitial) {
		super(trans.getConteneurParent());
		this.setObservateur(trans.getObservateur());
		
		this.setPseudoInitial(pseudoInitial);
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


	public void setEtatSource(PseudoInitial etat) {
		this._etatInit = etat;
	}

	public void setEtatDest(EtatIntermediaire etat) {
		this._etatDest = etat;		
	}

	public PseudoInitial getEtatSource() {
		return _etatInit;
	}

	public EtatIntermediaire getEtatDest() {
		return _etatDest;
	}
}
