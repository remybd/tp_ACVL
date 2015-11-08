package ElementsDiagramme;

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

	public Composite(ObservateurVue obs, String nom, Conteneur fils){
		super(obs, nom);
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
	public HashSet<ErreurEtat> chercherEtatsBloquants(){
		if(_fils != null)
			return _fils.chercherEtatsBloquants();
		else
			return new HashSet<ErreurEtat>();
	}
	

	/**
	 * Gestion erreur d'unicit� des �tats
	 * @return la liste des etats qui ont le m�me nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(){
		if(this._fils==null)
			return new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		return this._fils.chercherPluriciteEtats();
	}
	


	/**
	 * Retourne les erreurs de transition non d�terministes li�s � cet �tat
	 * 2 transitions sont non d�terministes si elles ont le m�me �v�nement et la m�me condition
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(){
		HashSet<TransitionNonDeterministe> transNnDeterm = super.chercherTransNnDeterm();
		
		if(this._fils==null)
			return transNnDeterm;
		
		transNnDeterm.addAll(this._fils.chercherTransNnDeterm());
		return transNnDeterm;			
	}
	
	@Override
	public void supprimer() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isEtatIntermediaire(){
		return true;
	}
	
	public boolean isTransition(){
    	return false;
    }

}
