package ElementsDiagramme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Controleurs.ControleurDiagramme;
import Erreurs.ErreurEtat;
import Erreurs.NonUnicite;
import Erreurs.TransitionNonDeterministe;
import Vues.ElementGraphique;
import Vues.EtatGraph;
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
	
	public HashSet<Element> getElements(){
		if(_fils == null)
			return new HashSet<Element>();
		
		return _fils.getElmts();
	}
	
	/**
	 * 
	 * @param l 
	 * @return Tous les éléments, même les sous-éléments des états composites
	 */
	public void getAllElements(ArrayList<Element> l){
		if(_fils != null)
			_fils.getAllElements(l);
	}

	/**
	 * 
	 * @return la liste des états qui sont bloquants au sein de cet état composite
	 */
	public HashSet<ErreurEtat> chercherEtatsBloquants(ObservateurVue zoneErreur){
		if(_fils != null)
			return _fils.chercherEtatsBloquants(zoneErreur);
		else
			return new HashSet<ErreurEtat>();
	}
	

	/**
	 * Gestion erreur d'unicité des états
	 * @return la liste des etats qui ont le même nom au sein d'un conteneur
	 */
	public HashMap<Conteneur,HashSet<NonUnicite>> chercherPluriciteEtats(ObservateurVue zoneErreur){
		if(this._fils==null)
			return new HashMap<Conteneur,HashSet<NonUnicite>>();
		
		return this._fils.chercherPluriciteEtats(zoneErreur);
	}
	


	/**
	 * Retourne les erreurs de transition non déterministes liés à cet état
	 * 2 transitions sont non déterministes si elles ont le même évènement et la même condition
	 * @return
	 */
	public HashSet<TransitionNonDeterministe> chercherTransNnDeterm(ObservateurVue zoneErreur){
		HashSet<TransitionNonDeterministe> transNnDeterm = super.chercherTransNnDeterm(zoneErreur);
		
		if(this._fils==null)
			return transNnDeterm;
		
		transNnDeterm.addAll(this._fils.chercherTransNnDeterm(zoneErreur));
		return transNnDeterm;			
	}

	public void applatir() throws Exception {
		_fils.applatir();

		//change la parenté des états et transition intermédiaires
		HashSet<Element> listEtatsAndTransitionIntermediaires = getEtatsAndTransitionIntermediaires();
		getConteneurParent().addElements(listEtatsAndTransitionIntermediaires);


		//change parentée au niveau graphique
		for(Element el : listEtatsAndTransitionIntermediaires){
			((ElementGraphique)el.getObservateur()).setParentPourAplatissement();
		}

		//relie toutes les transitions entrantes à l'état pointé par l'initial
		if(_fils.getPseudoInitial().getTransition() != null){
			EtatIntermediaire etatPointedByInit = _fils.getPseudoInitial().getTransition().getEtatDest();
			for(Transition t : this.getSources()){
				if(t.isTransitionIntermediaire()){
					ControleurDiagramme.instance().ajouterTransition(EnumTransition.INTER,t.getEtiquette(),(EtatGraph)t.getEtatSource().getObservateur(),(EtatGraph)etatPointedByInit.getObservateur());
				} else if(t.isTransitionInitiale()){
					ControleurDiagramme.instance().ajouterTransition(EnumTransition.INIT,t.getEtiquette(),(EtatGraph)t.getEtatSource().getObservateur(),(EtatGraph)etatPointedByInit.getObservateur());
				}
			}
		}

		//relie toutes les transitions sortantes aux états finaux
		HashSet<PseudoFinal> listEtatsFinaux = getEtatsFinaux();
		if(!listEtatsFinaux.isEmpty()){
			//il y a des états finaux, on les relie donc aux transitions sortantes
			for(EtatIntermediaire etatIntermediaire : getEtatsPointedByFinal(listEtatsFinaux)){
				for(Transition t : this.getDestinations()){
					if(t.isTransitionFinale()){//clone les transitions sortantes car peux y avoir plusieurs états finaux et il fuat donc les cloner
						ControleurDiagramme.instance().ajouterTransition(EnumTransition.FINAL,t.getEtiquette(),(EtatGraph)etatIntermediaire.getObservateur(),(EtatGraph)t.getEtatDestination().getObservateur());
					} else if(t.isTransitionIntermediaire()){
						ControleurDiagramme.instance().ajouterTransition(EnumTransition.INTER, t.getEtiquette(), (EtatGraph) etatIntermediaire.getObservateur(), (EtatGraph) t.getEtatDestination().getObservateur());
					}
				}
			}

		}

		//supprime les transitions sortantes puisqu'on les as clonées
		HashSet<Transition> clone = (HashSet<Transition>)this.getDestinations().clone();
		for(Transition t : clone){
			ControleurDiagramme.instance().supprimerElement((ElementGraphique)t.getObservateur());
		}
		this.getDestinations().clear();


		//supprime les transitions entrante puisqu'on les as clonées
		clone = (HashSet<Transition>)this.getSources().clone();
		for(Transition t : clone){
			ControleurDiagramme.instance().supprimerElement((ElementGraphique) t.getObservateur());
		}
		this.getSources().clear();


		//suprime les états finaux et leurs transitions dans l'état composite
		for(PseudoFinal pseudoFinal : listEtatsFinaux){
			ControleurDiagramme.instance().supprimerElement((ElementGraphique)pseudoFinal.getObservateur());
		}

		//suprime l'état initial et donc sa transition
		ControleurDiagramme.instance().supprimerElement((ElementGraphique) _fils.getPseudoInitial().getObservateur());
		//vide le conteneur
		_fils.getElmts().clear();
	}




	private HashSet<PseudoFinal> getEtatsFinaux(){
		HashSet<PseudoFinal> listEtatsFinaux = new HashSet<PseudoFinal>();

		for(Element e:_fils.getElmts()){
			if(e.isEtatPseudoFinal()){
				listEtatsFinaux.add((PseudoFinal)e);
			}
		}
		return listEtatsFinaux;
	}




	private HashSet<EtatIntermediaire> getEtatsPointedByFinal(HashSet<PseudoFinal> listEtatsFinaux){
		HashSet<EtatIntermediaire> listEtatsPointedByFinal = new HashSet<EtatIntermediaire>();

		for(PseudoFinal pf : listEtatsFinaux){
			for(TransitionFinale tf : pf.getTransitions()){
				listEtatsPointedByFinal.add((EtatIntermediaire)tf.getEtatSource());
			}
		}

		return listEtatsPointedByFinal;
	}



	private HashSet<Element> getEtatsAndTransitionIntermediaires(){
		HashSet<Element> listEtatsAndTransitionIntermediaires = new HashSet<Element>();

		for(Element e:_fils.getElmts()){
			if(e.isEtatIntermediaire()){
				listEtatsAndTransitionIntermediaires.add((EtatIntermediaire)e);
			} else if(e.isTransitionIntermediaire()){
				listEtatsAndTransitionIntermediaires.add((TransitionIntermediaire)e);
			}
		}
		return listEtatsAndTransitionIntermediaires;
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = super.supprimer(); //TODO : fonctionnel ?

		if(_fils == null)
			return elmtsSupr;

		elmtsSupr.addAll(_fils.supprimer());
		return elmtsSupr;
	}

	public ArrayList<Element> supprimerForAplatir() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);

		this.getConteneurParent().supprimerElmt(this);

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
