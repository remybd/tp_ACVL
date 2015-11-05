package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionIntermediaire extends Transition {
	private String _etiquette;
	private EtatIntermediaire _source;
	private EtatIntermediaire _dest;
	
	public TransitionIntermediaire(String etiquette, EtatIntermediaire etatSource, EtatIntermediaire etatDest){
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
