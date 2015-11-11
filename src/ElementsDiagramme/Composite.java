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

		EtatIntermediaire etatPointeByInit = _fils.getPseudoInitial().getTransition().getEtatDest();
		for(Transition t : this.getDestinations()){
			if(t.isTransitionFinale()){
//TODO ((TransitionFinale)t).setEtatSource();
			}
		}
		//relie toutes les transitions entrantes à l'état initial
		EtatIntermediaire etatPointedByInit = _fils.getPseudoInitial().getTransition().getEtatDest();
		for(Transition t : this.getSources()){
			if(t.isTransitionIntermediaire()){
				((TransitionIntermediaire)t).setEtatDest(etatPointedByInit);
			} else if(t.isTransitionInitiale()){
				((TransitionInitiale)t).setEtatDest(etatPointedByInit);
			}
		}


		HashSet<PseudoFinal> listEtatsFinaux = getEtatsFinaux();
		if(!listEtatsFinaux.isEmpty()){//il y a des états finaux que l'on relie aux transitions finales

			for(EtatIntermediaire etatIntermediaire : getEtatsPointedByFinal(listEtatsFinaux)){
				for(Transition t : this.getDestinations()){
					if(t.isTransitionFinale()){
						ControleurDiagramme.instance().ajouterTransition(EnumTransition.FINAL,t.getEtiquette(),(EtatGraph)etatIntermediaire.getObservateur(),(EtatGraph)t.getEtatDestination().getObservateur());

					} else if(t.isTransitionIntermediaire()){
						ControleurDiagramme.instance().ajouterTransition(EnumTransition.INTER, t.getEtiquette(), (EtatGraph) etatIntermediaire.getObservateur(), (EtatGraph) t.getEtatDestination().getObservateur());
					}
				}
			}

		} else {
			for(PseudoFinal pf : listEtatsFinaux){
				for(Transition t : pf.getTransitions()){
					ControleurDiagramme.instance().supprimerElement((ElementGraphique)t.getObservateur());
				}
			}
		}




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
