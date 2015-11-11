package ElementsDiagramme;

import java.util.ArrayList;

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
		this.attache(trans.getObservateur());
		
		this.setPseudoInitial(pseudoInitial);
		this.setEtatDest((EtatIntermediaire)trans.getEtatDestination());
	}
	
	public TransitionInitiale(TransitionFinale trans, PseudoInitial pseudoInitial) {
		super(trans.getConteneurParent());
		this.attache(trans.getObservateur());
		
		this.setPseudoInitial(pseudoInitial);
	}

	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}

	public void setPseudoInitial(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
		informe();
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_etatInit == null)
			return elmtsSupr;
		
		_etatInit.setTransition(null);
		this.getConteneurParent().supprimerElmt(this);
		return elmtsSupr;
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
		informe();
	}

	public void setEtatDest(EtatIntermediaire etat) {
		this._etatDest = etat;
		informe();
	}

	@Override
	public Etat getEtatSource() {
		return _etatInit;
	}

	@Override
	public Etat getEtatDestination() {
		return _etatDest;
	}

	@Override
	public String getEtiquette() {
		return "";
	}

	@Override
	public String getEvt() {
		return null;
	}

	@Override
	public String getGarde() {
		return null;
	}

	@Override
	public String getAction() {
		return null;
	}

	public EtatIntermediaire getEtatDest() {
		return _etatDest;
	}
}
