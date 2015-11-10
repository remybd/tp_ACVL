package ElementsDiagramme;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import Erreurs.Erreur;
import Erreurs.TransitionNonDeterministe;
import Tools.TableSymboles;
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
	
	public EtatIntermediaire(Conteneur parent, String nom){
		super(parent, nom);
	}
	
	/**
	 * Converti le pseudo initial spécifié en EtatIntermediaire
	 * @param init
	 */
	public EtatIntermediaire(PseudoInitial init){
		super(init.getConteneurParent(), init.getNom());
		this.setObservateur(init.getObservateur());
		
		TransitionInitiale transInit = init.getTransition();
		
		if(transInit != null)
			this.addDestination(new TransitionIntermediaire(transInit, this));
	}


	/**
	 * Converti le pseudo final spécifié en EtatIntermediaire
	 * @param init
	 */
	public EtatIntermediaire(PseudoFinal etat){
		super(etat.getConteneurParent(), etat.getNom());
		this.setObservateur(etat.getObservateur());
		
		HashSet<TransitionFinale> transFin = etat.getTransitions();
		
		if(transFin != null){
			Iterator<TransitionFinale> it = transFin.iterator();
			
			while(it.hasNext()){
				this.addDestination(new TransitionIntermediaire(it.next(), this));				
			}
		}
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
	
	
	public void unLinkSource(Transition t){
		if(_sources == null)
			return;
		
		_sources.remove(t);
	}
	
	public void unLinkDestination(Transition t){
		if(_dest == null)
			return;
		
		_dest.remove(t);
	}
	
	
	@Override
	public void supprimer() {
		for(TransitionIntermediaire trans : _sources){
			trans.supprimer();
		}
		
		for(TransitionIntermediaire trans : _dest){
			trans.supprimer();
		}	
		
	} 

	/**
	 * 
	 * @return true si l'état n'a pas de transition renvoyant vers un autre état
	 * 			false sinon
	 */
	public boolean estBloquant(){
		for(TransitionIntermediaire source : _sources){
			if(source.getEtatDest() != this)
				return true;
		}
		
		return false;
	}

	@Override
	public boolean isEtatIntermediaire() {
		return true;
	}
	

	/**
	 * Retourne les erreurs de transition non déterministes liés à cet état
	 * 2 transitions sont non déterministes si elles ont le même événement et la même condition
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(){
		HashSet<String> evtsConds = new HashSet<String>();
		HashSet<TransitionNonDeterministe> transNonDeterm = new HashSet<TransitionNonDeterministe>();
		String evtCond; //pr 1 transition, contient evenement+condition sans espaces
		String symbol;
		for(TransitionIntermediaire trans : _sources){
			evtCond = trans.getEvt()+trans.getAction();
			evtCond = evtCond.replaceAll("\\s", "");
			evtCond = evtCond.replaceAll("\\t", "");
			
			//evtsConds contient déjà ces infos -> trans est une transition non déterministe
			symbol = TableSymboles.get(evtCond);
			if(evtsConds.contains(symbol)){
				transNonDeterm.add(new TransitionNonDeterministe(trans, Erreur.ERR_TRANSITION_NON_DETERM));
			}
			else{
				evtsConds.add(symbol);
			}
		}
		
		return transNonDeterm;
	}
}
