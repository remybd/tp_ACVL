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
	
	/**
	 * 
	 * @return Les �l�ments du fils
	 */
	public HashSet<Element> getElements(){
		if(_fils == null)
			return new HashSet<Element>();
		
		return _fils.getElmts();
	}
	
	/**
	 * 
	 * @return Tous les �l�ments, m�me les sous-�l�ments des �tats composites
	 */
	public HashSet<Element> getAllElements(){
		if(_fils == null)
			return new HashSet<Element>();
		
		return _fils.getAllElements();
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






	public void applatir() throws Exception {
		_fils.applatir();

		//change la parenté des états et transition intermédiaires
		HashSet<Element> listEtatsAndTransitionIntermediaires = getEtatsAndTransitionIntermediaires();
		getConteneurParent().addElements(listEtatsAndTransitionIntermediaires);

		EtatGraph grandParent = ((ElementGraphique) getObservateur()).getParent();
		//change parentée au niveau graphique
		for(Element el : listEtatsAndTransitionIntermediaires){
			((ElementGraphique)el.getObservateur()).setParentPourAplatissement();
		}

		//relie toutes les transitions entrantes à l'état pointé par l'initial
		System.out.println("test : " + _fils.getPseudoInitial() + "   " + _fils.getPseudoInitial().getTransition());
		if(_fils.getPseudoInitial().getTransition() != null){
			EtatIntermediaire etatPointedByInit = _fils.getPseudoInitial().getTransition().getEtatDest();
			System.out.println("etatPointedByInit : " + etatPointedByInit.getNom());
			for(Transition t : this.getSources()){
				System.out.println("test");
				if(t.isTransitionIntermediaire()){
					((TransitionIntermediaire)t).setEtatDest(etatPointedByInit);
				} else if(t.isTransitionInitiale()){
					((TransitionInitiale)t).setEtatDest(etatPointedByInit);
				}
			}
		}



		//relie toutes les transitions sortantes aux états finaux
		HashSet<PseudoFinal> listEtatsFinaux = getEtatsFinaux();
		if(!listEtatsFinaux.isEmpty()){//il y a des états finaux, on les relie donc aux transitions sortantes

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
		for(Transition t : this.getDestinations()){
			ControleurDiagramme.instance().supprimerElement((ElementGraphique)t.getObservateur());
		}
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
