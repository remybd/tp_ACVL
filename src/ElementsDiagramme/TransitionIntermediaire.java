package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionIntermediaire extends Transition {
	private String _etiquette;
	private EtatIntermediaire _source;
	private EtatIntermediaire _dest;
	
	public TransitionIntermediaire(ObservateurVue obs, String etiquette, EtatIntermediaire etatSource, EtatIntermediaire etatDest){
		super(obs);
		this.setEtiquette(etiquette);
		this.setSource(etatSource);
		this.setDestination(etatDest);
	}
	
	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
	}

	public EtatIntermediaire getSource() {
		return _source;
	}

	public void setSource(EtatIntermediaire _source) {
		this._source = _source;
	}

	public EtatIntermediaire getDestination() {
		return _dest;
	}

	public void setDestination(EtatIntermediaire _dest) {
		this._dest = _dest;
	}

	@Override
	public void supprimer() {

	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isTransition() {
		return true;
	}
}
