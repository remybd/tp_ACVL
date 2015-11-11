package ElementsDiagramme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Erreurs.ErreurEtat;
import Erreurs.NonUnicite;
import Erreurs.TransitionNonDeterministe;
import Vues.ObservateurVue;



/**
 *  TODO
 * @author Thibaut
 *
 */
public class Composite extends EtatIntermediaire {
	private Conteneur _fils;

	public Composite(Conteneur parent, String nom, Conteneur fils){
		super(parent, nom);
		this.setFils(fils);
	}
	
	public Conteneur getFils() {
		return _fils;
	}

	public void setFils(Conteneur _fils) {
		this._fils = _fils;
	}
	
	/**
	 * 
	 * @return Les éléments du fils
	 */
	public HashSet<Element> getElements(){
		if(_fils == null)
			return new HashSet<Element>();
		
		return _fils.getElmts();
	}
	
	/**
	 * 
	 * @return Tous les éléments, même les sous-éléments des états composites
	 */
	public HashSet<Element> getAllElements(){
		if(_fils == null)
			return new HashSet<Element>();
		
		return _fils.getAllElmts();
	}

	/**
	 * 
	 * @return la liste des ï¿½tats qui sont bloquants au sein de cet ï¿½tat composite
	 */
	public HashSet<ErreurEtat> chercherEtatsBloquants(ObservateurVue zoneErreur){
		if(_fils != null)
			return _fils.chercherEtatsBloquants(zoneErreur);
		else
			return new HashSet<ErreurEtat>();
	}
	

	/**
	 * Gestion erreur d'unicitï¿½ des ï¿½tats
	 * @return la liste des etats qui ont le mï¿½me nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(ObservateurVue zoneErreur){
		if(this._fils==null)
			return new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		return this._fils.chercherPluriciteEtats(zoneErreur);
	}
	


	/**
	 * Retourne les erreurs de transition non dï¿½terministes liï¿½s ï¿½ cet ï¿½tat
	 * 2 transitions sont non dï¿½terministes si elles ont le mï¿½me ï¿½vï¿½nement et la mï¿½me condition
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(ObservateurVue zoneErreur){
		HashSet<TransitionNonDeterministe> transNnDeterm = super.chercherTransNnDeterm(zoneErreur);
		
		if(this._fils==null)
			return transNnDeterm;
		
		transNnDeterm.addAll(this._fils.chercherTransNnDeterm(zoneErreur));
		return transNnDeterm;			
	}
	
	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = super.supprimer(); //TODO : fonctionnel ?
		
		if(_fils == null)
			return elmtsSupr;
		
		elmtsSupr.addAll(_fils.supprimer());
		return elmtsSupr;
	}

	@Override
	public boolean isTransition() {
		return false;
	}

	@Override
	public boolean isEtatComposite() {
		return true;
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
		return false;
	}

}
