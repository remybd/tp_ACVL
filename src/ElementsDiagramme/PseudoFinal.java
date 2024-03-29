package ElementsDiagramme;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoFinal  extends Etat {
	private HashSet<TransitionFinale> _trans = new HashSet<TransitionFinale>();

	public PseudoFinal(Conteneur parent, String nom){
		super(parent, nom);
	}
	

	/**
	 * Converti le pseudo intial spécifié en PseudoFinal ; la transition associée est perdue
	 * @param 
	 */
	public PseudoFinal(PseudoInitial init){
		super(init.getConteneurParent(), init.getNom());
		this.attache(init.getObservateur());
	}
	

	/**
	 * Converti le EtatIntermediaire spécifié en PseudoFinal
	 * @param 
	 */
	public PseudoFinal(EtatIntermediaire etat){
		super(etat.getConteneurParent(), etat.getNom());
		this.attache(etat.getObservateur());
		
		for(Transition trans : etat.getDestinations()){
			if(trans.isTransitionInitiale()){
				this.addTransition(new TransitionFinale((TransitionInitiale)trans, this));					
			}
			else if(trans.isTransitionIntermediaire()){
				this.addTransition(new TransitionFinale((TransitionIntermediaire)trans, this));					
			}
			else{
				this.addTransition((TransitionFinale)trans);					
			}
		}
	}
	
	public HashSet<TransitionFinale> getTransitions() {
		return _trans;
	}

	public void addTransition(TransitionFinale trans) {
		if(_trans == null)
			_trans = new HashSet<TransitionFinale>();
			
		this._trans.add(trans);
	}

	public void unLinkTransition(TransitionFinale transitionFinale) {
		if(_trans != null)
			_trans.remove(transitionFinale);	
	}
	
	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		HashSet<TransitionFinale> clone = (HashSet<TransitionFinale>)_trans.clone();
		for(TransitionFinale trans : clone){
			elmtsSupr.add(trans);
			trans.supprimer();
		}
		_trans.clear();

		this.getConteneurParent().supprimerElmt(this);
		return elmtsSupr;
	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isEtatComposite() {
		return false;
	}

	@Override
	public boolean isEtatSimple() {
		return false;
	}

	@Override
	public boolean isEtatPseudoInitial() {
		return false;
	}

	@Override
	public boolean isEtatPseudoFinal() {
		return true;
	}

}