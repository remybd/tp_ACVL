package ElementsDiagramme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Controleurs.ControleurDiagramme;
import Erreurs.Erreur;
import Erreurs.ErreurEtat;
import Erreurs.NonUnicite;
import Erreurs.TransitionNonDeterministe;
import Tools.TableSymboles;
import Vues.ObservateurVue;

/**
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
		ArrayList<Element> elmtsDuConteneur = new ArrayList<Element>();

		for(Element elmt : this._elmts){
			elmtsDuConteneur.add(elmt);
		}
		
		if(_etatInit != null){
			elmtsSupr.add(_etatInit);
			_etatInit.supprimer();
		}
		
		for(Element elmt : elmtsDuConteneur){
			elmtsSupr.add(elmt);
			elmt.supprimer();
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
	 * Gestion erreur d'�tats bloquants
	 * @return la liste des �tats qui sont bloquants
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
	 * Gestion erreur de transitions non d�terministes
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
	
	public void getAllElements(ArrayList<Element> l) {		
		if(_elmts != null) {
			for(Element elmt : _elmts){
				l.add(elmt);
				if(elmt.isEtatComposite()){
					((Composite)elmt).getAllElements(l);
				}
				
			}
		}
	}

	public void addElmt(Element _elmts) {
		if(this._elmts == null)
			this._elmts = new HashSet<Element>();
			
		this._elmts.add(_elmts);
	}

	public void applatir() throws Exception {
		HashSet<Element> temp = (HashSet<Element>)_elmts.clone();

		for(Element el: temp){
			if(el.isEtatComposite()){
				((Composite)el).applatir();

				ControleurDiagramme.instance().supprimerElementForAplatir((Composite) el);
			}
		}
	}

	public void addElements(HashSet<Element> elements){
		for(Element ti : elements){
			ti.setConteneurParent(this);
			this.addElmt(ti);
		}
	}

	public void setElmts(HashSet<Element> elements){
		_elmts = elements;
	}
}
