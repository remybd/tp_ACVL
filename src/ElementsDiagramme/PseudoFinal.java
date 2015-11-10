package ElementsDiagramme;

import java.util.HashSet;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoFinal  extends Etat {
	private HashSet<TransitionFinale> _trans = new HashSet<TransitionFinale>();

	public PseudoFinal(Conteneur parent, String nom, TransitionFinale transitionFinale){
		super(parent, nom);
		this.addTransition(transitionFinale);
	}
	

	/**
	 * Converti le pseudo intial spécifié en PseudoFinal ; la transition associée est perdue
	 * @param 
	 */
	public PseudoFinal(PseudoInitial init){
		super(init.getConteneurParent(), init.getNom());
		this.setObservateur(init.getObservateur());
	}
	

	/**
	 * Converti le EtatIntermediaire spécifié en PseudoFinal
	 * @param 
	 */
	public PseudoFinal(EtatIntermediaire etat){
		super(etat.getConteneurParent(), etat.getNom());
		this.setObservateur(etat.getObservateur());
		
		for(TransitionIntermediaire trans : etat.getDestinations()){
			this.addTransition(new TransitionFinale(trans, this));			
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
	public void supprimer() {
		for(TransitionFinale trans : _trans){
			trans.supprimer();
		}
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
