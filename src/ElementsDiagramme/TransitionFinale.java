package ElementsDiagramme;

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
	public void supprimer() {
		if(_etatFinal == null)
			return;
		
		_etatFinal.unLinkTransition(this);
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

	@Override
	public EtatIntermediaire getEtatSource() {
		return _etatSource;
	}


	@Override
	public void setEtatSource(Etat etat) {
		this._etatSource = etat;
	}

	@Override
	public void setEtatDest(Etat etat) {
		this._etatFinal = etat;
	}

	@Override
	public Etat getEtatDest() {
		return _etatFinal;
	}
}
