package ElementsDiagramme;

import java.util.HashMap;
import java.util.HashSet;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class EtatIntermediaire extends Etat{
	//transitions dont la destination est this
	private HashSet<TransitionIntermediaire> _dest = new HashSet<TransitionIntermediaire>();
	//transitions dont la source est this
	private HashSet<TransitionIntermediaire> _sources = new HashSet<TransitionIntermediaire>();
	
	public EtatIntermediaire(ObservateurVue obs, String nom){
		super(obs, nom);
	}
	
	public HashSet<TransitionIntermediaire> getDestinations() {
		return _dest;
	}
	public void addDestination(TransitionIntermediaire dest) {
		if(this._dest == null)
			this._dest = new HashSet<TransitionIntermediaire>();
			
		this._dest.add(dest);
	}
    public void setDestinations(HashSet<TransitionIntermediaire> destinations){
        _dest = destinations;
    }
	
	public HashSet<TransitionIntermediaire> getSources() {
		return _sources;
	}
	public void addSource(TransitionIntermediaire source) {
		if(this._sources == null)
			this._sources = new HashSet<TransitionIntermediaire>();
			
		this._sources.add(source);
	}
	public void setSources(HashSet<TransitionIntermediaire> sources){
        _sources = sources;
    }

	/**
	 * 
	 * @return true si l'état n'a pas de transition renvoyant vers un autre état
	 * 			false sinon
	 */
	public boolean estBloquant(){
		for(TransitionIntermediaire source : _sources){
			if(source.getDestination() != this)
				return true;
		}
		
		return false;
	}

	@Override
	public boolean isEtatIntermediaire() {
		return true;
	}
}
