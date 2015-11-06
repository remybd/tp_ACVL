package ElementsDiagramme;

import java.util.HashMap;
import java.util.HashSet;

import Erreurs.Erreur;
import Erreurs.NonUnicite;
import Tools.TableSymboles;

public class Conteneur {
	private final int ERR_UNICITE = 1;
	
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
	 * @return La liste des erreurs trouv�es dans 
	 */
	public HashSet<Erreur> chercherErreurs(){
		return null;
	}
	
	/**
	 * Gestion erreur d'unicit� des �tats
	 * @return la liste des etats qui ont le m�me nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(){
		HashMap<Conteneur,HashSet<NonUnicite>> etatsIdentiques = new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		HashMap<String, EtatIntermediaire> nomsUtilises = new HashMap<String, EtatIntermediaire>();
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				String nomEtat = TableSymboles.get( ((EtatIntermediaire) elmt).getNom() );
				
				//l'�tat a un nom d�j� utilis�
				if(nomsUtilises.containsKey(nomEtat)){
					
					//this n'a encore jamais �t� r�pertori�
					if(!etatsIdentiques.containsKey(this)){
						etatsIdentiques.put(this, new HashSet<NonUnicite>() );
					}
					
					//on ajoute l'erreur
					NonUnicite erreur = new NonUnicite((Etat)elmt, nomsUtilises.get(nomEtat), ERR_UNICITE);
					etatsIdentiques.get(this).add(erreur);					
				}
				else
					nomsUtilises.put(nomEtat, (EtatIntermediaire) elmt);
				
				//si l'�l�ment est un composite, on doit y faire les m�mes v�rifications
				if(elmt instanceof Composite){
					etatsIdentiques.putAll( ((Composite)elmt).chercherPluriciteEtats() );
				}
			}
		}
		
		return etatsIdentiques;
	}
	
	/**
	 * Gestion erreur d'�tats bloquants
	 * @return la liste des �tats qui sont bloquants
	 */
	public HashSet<EtatIntermediaire> chercherEtatsBloquants(){
		HashSet<EtatIntermediaire> etatsBloquants = new HashSet<EtatIntermediaire>(); 
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				if(((EtatIntermediaire)elmt).estBloquant())
					etatsBloquants.add((EtatIntermediaire)elmt);
				
				//si l'elmt est un �tat composite : nous devons d�tecter les erreurs au sein de celui-ci
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
