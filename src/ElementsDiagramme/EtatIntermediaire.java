package ElementsDiagramme;

import java.util.ArrayList;
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
	private HashSet<Transition> _dest = new HashSet<Transition>();
	//transitions dont la source est this
	private HashSet<Transition> _sources = new HashSet<Transition>();
	
	public EtatIntermediaire(Conteneur parent, String nom){
		super(parent, nom);
	}
	
	/**
	 * Converti le pseudo initial spécifié en EtatIntermediaire
	 * @param init
	 */
	public EtatIntermediaire(PseudoInitial init){
		super(init.getConteneurParent(), init.getNom());
		this.attache(init.getObservateur());
		
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
		this.attache(etat.getObservateur());
		
		HashSet<TransitionFinale> transFin = etat.getTransitions();
		
		if(transFin != null){
			Iterator<TransitionFinale> it = transFin.iterator();
			
			while(it.hasNext()){
				this.addDestination(new TransitionIntermediaire(it.next(), this));				
			}
		}
	}
	
	public HashSet<Transition> getDestinations() {
		return _dest;
	}
	public void addDestination(Transition dest) {
        if(this._dest == null)
            this._dest = new HashSet<Transition>();

        this._dest.add(dest);
    }
    public void setDestinations(HashSet<Transition> destinations){
        _dest = destinations;
    }
	
	public HashSet<Transition> getSources() {
		return _sources;
	}
	public void addSource(TransitionIntermediaire source) {
		if(this._sources == null)
			this._sources = new HashSet<Transition>();
			
		this._sources.add(source);
	}
	
	public void setSources(HashSet<Transition> sources){
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
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		for(Transition trans : _sources){
			trans.supprimer();
			elmtsSupr.add(trans);
		}
		
		for(Transition trans : _dest){
			trans.supprimer();
			elmtsSupr.add(trans);
		}
		
		this.getConteneurParent().supprimerElmt(this);
		
		return elmtsSupr;
	} 

	/**
	 * 
	 * @return true si l'état n'a pas de transition renvoyant vers un autre état
	 * 			false sinon
	 */
	public boolean estBloquant(){
		for(Transition source : _sources){
			if(source.getEtatDestination() != this)
				return false;
		}
		
		return true;
	}

	@Override
	public boolean isEtatIntermediaire() {
		return true;
	}

	@Override
	public boolean isEtatPseudoInitial() {
		return false;
	}

	@Override
	public boolean isEtatPseudoFinal() {
		return false;
	}
	

	/**
	 * Retourne les erreurs de transition non déterministes liés à cet état
	 * 2 transitions sont non déterministes si elles ont le même événement et la même condition
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(ObservateurVue zoneErreur){
		HashSet<String> evtsConds = new HashSet<String>();
		HashSet<TransitionNonDeterministe> transNonDeterm = new HashSet<TransitionNonDeterministe>();
		String evtCond; //pr 1 transition, contient evenement+condition sans espaces
		String symbol;
		for(Transition trans : _sources){
			evtCond = trans.getEvt()+trans.getAction();
			evtCond = evtCond.replaceAll("\\s", "");
			evtCond = evtCond.replaceAll("\\t", "");
			
			//evtsConds contient déjà ces infos -> trans est une transition non déterministe
			symbol = TableSymboles.get(evtCond);
			if(evtsConds.contains(symbol)){
				transNonDeterm.add(new TransitionNonDeterministe(trans, Erreur.ERR_TRANSITION_NON_DETERM, zoneErreur));
			}
			else{
				evtsConds.add(symbol);
			}
		}
		
		return transNonDeterm;
	}
}
