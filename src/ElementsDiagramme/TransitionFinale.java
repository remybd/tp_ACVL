package ElementsDiagramme;

import java.util.HashSet;

import Erreurs.Erreur;
import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionFinale extends Transition {
	private String _etiquette;
	private HashSet<PseudoFinal> _etatsFinaux = new HashSet<PseudoFinal>();
	
	public TransitionFinale(String etiquette){
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
