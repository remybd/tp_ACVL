package ElementsDiagramme;

import java.util.HashSet;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class PseudoInitial extends Etat {
	private TransitionInitiale _trans;

	public PseudoInitial(Conteneur parent, String nom){
		super(parent, nom);
	}
	
	public PseudoInitial(Conteneur parent, String nom, TransitionInitiale transition){
		super(parent, nom);
		this.setTransition(transition);
	}



	/**
	 * Converti le PseudoFinal sp�cifi� en PseudoInitial ; la transition associ�e est perdue
	 * @param 
	 */
	public PseudoInitial(PseudoFinal init){
		super(init.getConteneurParent(), init.getNom());
		this.setObservateur(init.getObservateur());
	}
	

	/**
	 * Converti le EtatIntermediaire sp�cifi� en PseudoInitial
	 * @param 
	 */
	public PseudoInitial(EtatIntermediaire etat){
		super(etat.getConteneurParent(), etat.getNom());
		this.setObservateur(etat.getObservateur());
		
		HashSet<TransitionIntermediaire> trans = etat.getDestinations();
		
		if(trans != null && trans.size()>0)
			this.setTransition(new TransitionInitiale(trans.iterator().next(), this));
	}
	
	public TransitionInitiale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionInitiale _trans) {
		this._trans = _trans;
	}
	

	@Override
	public void supprimer() {
		if(_trans == null)
			return;
		
		_trans.supprimer();	
	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isTransition() {
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
		return true;
	}

	@Override
	public boolean isEtatPseudoFinal() {
		return false;
	}
}
