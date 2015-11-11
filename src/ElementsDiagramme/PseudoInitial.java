package ElementsDiagramme;

import java.util.ArrayList;
import java.util.HashSet;

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
	 * Converti le PseudoFinal spécifié en PseudoInitial ; la transition associée est perdue
	 * @param 
	 */
	public PseudoInitial(PseudoFinal init){
		super(init.getConteneurParent(), init.getNom());
		this.attache(init.getObservateur());
	}
	

	/**
	 * Converti le EtatIntermediaire spécifié en PseudoInitial
	 * @param 
	 */
	public PseudoInitial(EtatIntermediaire etat){
		super(etat.getConteneurParent(), etat.getNom());
		this.attache(etat.getObservateur());
		
		HashSet<Transition> trans = etat.getDestinations();
		
		if(trans != null && trans.size()>0){
			Transition tr = trans.iterator().next();
			if(tr.isTransitionFinale())
				this.setTransition(new TransitionInitiale((TransitionFinale)tr, this));
			else if(tr.isTransitionIntermediaire())
				this.setTransition(new TransitionInitiale((TransitionIntermediaire)tr, this));
			else
				this.setTransition((TransitionInitiale)tr);
		}
	}
	
	public TransitionInitiale getTransition() {
		return _trans;
	}

	public void setTransition(TransitionInitiale _trans) {
		this._trans = _trans;
	}
	

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_trans == null)
			return elmtsSupr;
		
		_trans.supprimer();	
		elmtsSupr.add(_trans);
		this.getConteneurParent().supprimerElmt(this);
		return elmtsSupr;
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
