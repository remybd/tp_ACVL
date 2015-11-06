package ElementsDiagramme;

import java.util.HashSet;

import Erreurs.Erreur;

public class Conteneur {
	private PseudoInitial _etatInit;
	private HashSet<Element> _elmts = new HashSet<Element>();;
	private HashSet<Erreur> _erreurs = new HashSet<Erreur>();
	
	public Conteneur(PseudoInitial etatInit){
		this.setPseudoInital(etatInit);
	}


	public void supprimer(){
		for(Element elmt : this._elmts){
			elmt.supprimer();
		}
	}
	
	
	/** TODO
	 * 
	 * @return La liste des erreurs trouvées dans 
	 */
	public HashSet<Erreur> chercherErreurs(){
		return null;
	}
	
	/**
	 * 
	 * @return la liste des états qui sont bloquants
	 */
	public HashSet<EtatIntermediaire> chercherEtatsBloquants(){
		HashSet<EtatIntermediaire> etatsBloquants = new HashSet<EtatIntermediaire>(); 
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				if(((EtatIntermediaire)elmt).estBloquant())
					etatsBloquants.add((EtatIntermediaire)elmt);
				
				//si l'elmt est un état composite : nous devons détecter les erreurs au sein de celui-ci
				if(elmt instanceof Composite)
					etatsBloquants.addAll(((Composite)elmt).chercherEtatsBloquants());
			}
							
		}
		
		return etatsBloquants;
	}
	
	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}
	public void setPseudoInital(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
	}
	public HashSet<Erreur> getErreurs() {
		return _erreurs;
	}
	public void addErreur(Erreur _erreur) {
		if(this._erreurs == null)
			_erreurs = new HashSet<Erreur>();
			
		this._erreurs.add(_erreur);
	}

	public HashSet<Element> getElmts() {
		return _elmts;
	}

	public void addElmt(Element _elmts) {
		if(this._elmts == null)
			this._elmts = new HashSet<Element>();
			
		this._elmts.add(_elmts);
	}	
}
