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
	 * @return la liste des �tats qui sont bloquants au sein de cet �tat composite
	 */
	public HashSet<ErreurEtat> chercherEtatsBloquants(ObservateurVue zoneErreur){
		if(_fils != null)
			return _fils.chercherEtatsBloquants(zoneErreur);
		else
			return new HashSet<ErreurEtat>();
	}
	

	/**
	 * Gestion erreur d'unicit� des �tats
	 * @return la liste des etats qui ont le m�me nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(ObservateurVue zoneErreur){
		if(this._fils==null)
			return new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		return this._fils.chercherPluriciteEtats(zoneErreur);
	}
	


	/**
	 * Retourne les erreurs de transition non d�terministes li�s � cet �tat
	 * 2 transitions sont non d�terministes si elles ont le m�me �v�nement et la m�me condition
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
		
		elmtsSupr.add(this);
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
