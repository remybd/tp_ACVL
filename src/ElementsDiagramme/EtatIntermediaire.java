package ElementsDiagramme;

import java.util.HashSet;

import Erreurs.Erreur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class EtatIntermediaire extends Etat{
	private HashSet<TransitionIntermediaire> _dest = new HashSet<TransitionIntermediaire>();
	private HashSet<TransitionIntermediaire> _source = new HashSet<TransitionIntermediaire>();
	
	public EtatIntermediaire(String nom){
		super(nom);
	}
	
	public HashSet<TransitionIntermediaire> getDestinations() {
		return _dest;
	}
	public void addDestination(TransitionIntermediaire dest) {
		if(this._dest == null)
			this._dest = new HashSet<TransitionIntermediaire>();
			
		this._dest.add(dest);
	}
	
	public HashSet<TransitionIntermediaire> getSources() {
		return _source;
	}
	public void setSource(TransitionIntermediaire source) {
		if(this._source == null)
			this._source = new HashSet<TransitionIntermediaire>();
			
		this._source.add(source);
	}
	
}
