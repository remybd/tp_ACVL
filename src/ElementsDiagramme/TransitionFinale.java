package ElementsDiagramme;

import java.util.ArrayList;
import java.util.HashSet;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionFinale extends Transition {
	private String _etiquette;
	private PseudoFinal _etatFinal;
	private EtatIntermediaire _etatSource;
	
	public TransitionFinale(Conteneur parent, String etiquette){
		super(parent);
		this.setEtiquette(etiquette);
	}
	

	/**
	 * Converti la TransitionIntermediaire spécifiée en TransitionFinale
	 * @param trans
	 * @param pseudoFinal
	 */
	public TransitionFinale(TransitionIntermediaire trans, PseudoFinal pseudoFinal) {
		super(trans.getConteneurParent());
		this.setObservateur(trans.getObservateur());
		
		this.setEtiquette(trans.getEtiquette());
		this.setEtatSource(trans.getEtatSource());
		this.setPseudoFinal(pseudoFinal);
	}

	/**
	 * Converti la TransitionInitiale spécifiée en TransitionFinale ; l'état destination est perdu
	 * @param trans
	 * @param pseudoFinal
	 */
	public TransitionFinale(TransitionInitiale trans, PseudoFinal pseudoFinal) {
		super(trans.getConteneurParent());
		this.setObservateur(trans.getObservateur());
		
		this.setEtiquette("");
		this.setPseudoFinal(pseudoFinal);
	}


	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
	}
	
	public PseudoFinal getPseudoFinal(){
		return this._etatFinal;
	}
	
	public void setPseudoFinal(PseudoFinal etatFinal){
		this._etatFinal = etatFinal;
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_etatFinal == null)
			return elmtsSupr;
		
		_etatFinal.unLinkTransition(this);
		return elmtsSupr;
	}


	@Override
	public boolean isTransitionFinale() {
		return true;
	}

	@Override
	public boolean isTransitionInitiale() {
		return false;
	}

	@Override
	public boolean isTransitionIntermediaire() {
		return false;
	}

	public EtatIntermediaire getEtatSource() {
		return _etatSource;
	}


	public void setEtatSource(EtatIntermediaire etat) {
		this._etatSource = etat;
	}

	public void setEtatDest(PseudoFinal etat) {
		this._etatFinal = etat;
	}

	public PseudoFinal getEtatDest() {
		return _etatFinal;
	}
}
