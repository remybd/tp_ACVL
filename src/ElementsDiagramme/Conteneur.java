package ElementsDiagramme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Erreurs.Erreur;
import Erreurs.ErreurEtat;
import Erreurs.NonUnicite;
import Erreurs.TransitionNonDeterministe;
import Tools.TableSymboles;
import Vues.ObservateurVue;

/**
 * TODO : instanceof : bien ou pas bien ?
 * @author Thibaut
 *
 */
public class Conteneur implements Serializable {
	private static final long serialVersionUID = 42L;
	
	private PseudoInitial _etatInit;
	private HashSet<Element> _elmts = new HashSet<Element>();;
	private HashSet<Erreur> _erreurs = new HashSet<Erreur>();
	
	public Conteneur(PseudoInitial etatInit){
		this.setPseudoInital(etatInit);
	}
	public Conteneur(){
		super();
	}

	public ArrayList<Element> supprimer(){
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		
		if(_etatInit != null){
			_etatInit.supprimer();
			elmtsSupr.add(_etatInit);
		}
		
		for(Element elmt : this._elmts){
			elmt.supprimer();
			elmtsSupr.add(elmt);
		}
		
		_erreurs = null;
		return elmtsSupr;
	}
	
	public void supprimerElmt(Element elmt){
		_elmts.remove(elmt);
	}
	
	/**
	 * 
	 * @return La liste des erreurs trouvées dans le conteneur this et tous ses états composites
	 */
	public HashSet<Erreur> chercherErreurs(ObservateurVue zoneErreur){
		HashSet<Erreur> erreurs = new HashSet<Erreur>();
		erreurs.addAll(this.chercherEtatsBloquants(zoneErreur));
		erreurs.addAll(this.chercherTransNnDeterm(zoneErreur));
		
		HashMap<Conteneur,HashSet<NonUnicite>> etatsDupliques = this.chercherPluriciteEtats(zoneErreur);
		for(Conteneur cont : etatsDupliques.keySet()){
			erreurs.addAll(etatsDupliques.get(cont));
		}
		
		return erreurs;
	}
	
	/**
	 * Gestion erreur d'unicité des états
	 * @return la liste des etats qui ont le même nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(ObservateurVue zoneErreur){
		HashMap<Conteneur,HashSet<NonUnicite>> etatsIdentiques = new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		HashMap<String, EtatIntermediaire> nomsUtilises = new HashMap<String, EtatIntermediaire>();
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				String nomEtat = TableSymboles.get( ((EtatIntermediaire) elmt).getNom() );
				
				//l'état a un nom déjà utilisé
				if(nomsUtilises.containsKey(nomEtat)){
					
					//this n'a encore jamais été répertorié
					if(!etatsIdentiques.containsKey(this)){
						etatsIdentiques.put(this, new HashSet<NonUnicite>() );
					}
					
					//on ajoute l'erreur
					NonUnicite erreur = new NonUnicite((Etat)elmt, nomsUtilises.get(nomEtat), Erreur.ERR_UNICITE_ETAT, zoneErreur);
					etatsIdentiques.get(this).add(erreur);					
				}
				else
					nomsUtilises.put(nomEtat, (EtatIntermediaire) elmt);
				
				//si l'élément est un composite, on doit y faire les mêmes vérifications
				if(elmt instanceof Composite){
					etatsIdentiques.putAll( ((Composite)elmt).chercherPluriciteEtats(zoneErreur) );
				}
			}
		}
		
		return etatsIdentiques;
	}
	
	/**
	 * Gestion erreur d'états bloquants
	 * @return la liste des états qui sont bloquants
	 */
	public HashSet<ErreurEtat> chercherEtatsBloquants(ObservateurVue zoneErreur){
		HashSet<ErreurEtat> etatsBloquants = new HashSet<ErreurEtat>(); 
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				if(((EtatIntermediaire)elmt).estBloquant())
					etatsBloquants.add(new ErreurEtat("Etat bloquant", (Etat) elmt, Erreur.ERR_ETAT_BLOQUANT, zoneErreur));
				
				//si l'elmt est un état composite : nous devons détecter les erreurs au sein de celui-ci
				if(elmt instanceof Composite)
					etatsBloquants.addAll(((Composite)elmt).chercherEtatsBloquants(zoneErreur));
			}
							
		}
		
		return etatsBloquants;
	}
	
	/**
	 * Gestion erreur de transitions non déterministes
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(ObservateurVue zoneErreur){
		HashSet<TransitionNonDeterministe> transNnDeterm = new HashSet<TransitionNonDeterministe>();
		
		for(Element elmt : this._elmts){
			if(elmt instanceof EtatIntermediaire){
				if(elmt instanceof EtatIntermediaire)
					transNnDeterm.addAll( ((EtatIntermediaire)elmt).chercherTransNnDeterm(zoneErreur));
			}
		}
		
		return transNnDeterm;
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
	
	public HashSet<Element> getAllElements() {
		HashSet<Element> elmts = new HashSet<Element>(); 
		
		if(_elmts == null)
			return elmts;
		
		for(Element elmt : _elmts){
			if(elmt.isEtatComposite()){
				elmts.addAll( ((Composite)elmt).getAllElements());
			}
			
			elmts.add(elmt);
		}
		
		return elmts;
	}

	public void addElmt(Element _elmts) {
		if(this._elmts == null)
			this._elmts = new HashSet<Element>();
			
		this._elmts.add(_elmts);
	}

	public void applatir(){

		for(Element el: _elmts){
			if(el.isEtatComposite()){
				((Composite)el).applatir();
			}
		}
	}
}
