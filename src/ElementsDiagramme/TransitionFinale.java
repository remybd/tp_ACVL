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
	private HashSet<PseudoFinal> _etatsFinaux = new HashSet<PseudoFinal>();
	
	public TransitionFinale(ObservateurVue obs, String etiquette){
		super(obs);
		this.setEtiquette(etiquette);
	}
	
	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
	}
	
	public HashSet<PseudoFinal> getPseudoFinaux(){
		return this._etatsFinaux;
	}
	
	public void addPseudoFinal(PseudoFinal etatFinal){
		if(this._etatsFinaux == null)
			_etatsFinaux = new HashSet<PseudoFinal>();
			
		this._etatsFinaux.add(etatFinal);
	}
}
