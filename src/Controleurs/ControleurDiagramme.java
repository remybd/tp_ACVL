package Controleurs;

import ElementsDiagramme.*;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;
import Erreurs.Erreur;
import Exceptions.*;
import Vues.*;
import com.mxgraph.model.mxCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by r�my on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private Ihm ihm;

    private HashMap<ElementGraphique,Element> correspondance;
    private HashSet<Erreur> erreurs = new HashSet<Erreur>();
    final private static ControleurDiagramme instanceUnique = new ControleurDiagramme();

    private ControleurDiagramme(){
        this.ihm = Ihm.instance();
        this.mainConteneur = new Conteneur();
        this.correspondance = new HashMap<ElementGraphique,Element>();
    }

    static public ControleurDiagramme instance() {
        return instanceUnique;
    }

    public void init(){
        PseudoInitial pi = null;

        try {
            pi = (PseudoInitial)ajouterEtat(EnumEtat.INIT,"",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pi.setConteneurParent(mainConteneur);
        mainConteneur.setPseudoInital(pi);

    }

    //TODO A modifier, ajouterTransition doit recevoir des EtatGraph de la Vue et non pas des �tats
    public Transition ajouterTransition(EnumTransition type, String etiquette, EtatGraph s, EtatGraph d) throws Exception {
        Etat sEtat = (Etat)getElementFromGraphic(s);
        Etat dEtat = (Etat)getElementFromGraphic(d);

        Conteneur conteneurParent = sEtat.getConteneurParent();
        Transition t = Transition.creerTransition(type,etiquette,sEtat,dEtat,conteneurParent);

        TransitionGraph tg = ihm.createTransitionGraph(s,d,t);
        t.attache(tg);

        conteneurParent.addElmt(t);
        correspondance.put(tg,t);

        this.chercherErreurs();
        return t;
    }

    public Etat ajouterEtat(EnumEtat type, String nom, EtatGraph parent) throws Exception {
        Conteneur conteneurParent;
        if(parent == null){
            conteneurParent = mainConteneur;
        } else {
            //TODO Exception de cast (l'objet qu'on r�cup�re si le parent != null n'est pas forc�ment un Composite, je ne sais pas pq)
            conteneurParent = ((Composite)getElementFromGraphic(parent)).getFils();
        }
        Etat e = Etat.creerEtat(type,nom,this,conteneurParent);

        EtatGraph eg = ihm.createEtatGraph(parent,e);
        e.attache(eg);

        conteneurParent.addElmt(e);
        correspondance.put(eg,e);

        if(type == EnumEtat.COMPOSITE){
            PseudoInitial init = (PseudoInitial)this.ajouterEtat(EnumEtat.INIT, nom, eg);
            ((Composite)e).getFils().setPseudoInital(init);

            PseudoInitial psi = ((Composite) e).getFils().getPseudoInitial();
            psi.setConteneurParent(((Composite) e).getFils());
            getEtatGraphFromEtat(psi).setParent(eg);
        }

        this.chercherErreurs();
        return e;
    }

    public void renommerEtat(EtatGraph eg, String nom) throws NameNotModifiableException {
        Etat e = (Etat)getElementFromGraphic(eg);

        if(e.isEtatIntermediaire()){
            e.setNom(nom);
        } else {
            throw new NameNotModifiableException();
        }
        this.chercherErreurs();
    }

    public void supprimerElement(ElementGraphique elem){
        Element e = getElementFromGraphic(elem);

        ArrayList<Element> elementsSupprime = e.supprimer();
        for(Element element : elementsSupprime){

            ihm.removeElem(getElemGraphFromElem(element).getObjet_graphique());
            correspondance.remove(elem);
        }
        
        DEBUG_displayElmts();
        this.chercherErreurs();
    }

    public void supprimerElementForAplatir(Composite e) throws Exception {
        EtatGraph elem = getEtatGraphFromEtat(e);

        ArrayList<Element> elementsSupprime = e.supprimerForAplatir();
        for(Element element : elementsSupprime){

            ihm.removeElem(getElemGraphFromElem(element).getObjet_graphique());
            correspondance.remove(elem);
        }

        DEBUG_displayElmts();
        this.chercherErreurs();
    }

    public void DEBUG_displayElmts(){
    	System.out.println("Elmts pr�sents dans le controleur :");

    	int i=0;
    	for(ElementGraphique elmtGraph : correspondance.keySet()){
			System.out.println("Element "+i);
			
    		Element elmt = correspondance.get(elmtGraph);
    		if(elmt.isEtat()){
    			System.out.println("Etat : "+((Etat)elmt).getNom());
    		}
    		else if(elmt.isTransition()){
    			System.out.println("Transition : "+((Transition)elmt).getEtiquette());
    		}
    		
    		i++;
    	}
    }
    
    public void modifierTransition(TransitionGraph transitionGraph, EtatGraph source, EtatGraph dest,String etiquette) throws Exception{
    	/* Check les pr�conditions */
    	if(!correspondance.containsKey(transitionGraph))
    		throw new Exception("La transition sp�cifi�e n'existe pas");
    	
    	if(!correspondance.containsKey(source))
    		throw new Exception("L'�tat source sp�cifi� n'existe pas");
    	
    	if(!correspondance.containsKey(dest))
    		throw new Exception("L'�tat destination sp�cifi� n'existe pas");
    	
    	
    	
    	/* Get les correspondances dans le mod�le */
    	Element modelTrans = correspondance.get(transitionGraph);
    	if(!modelTrans.isTransition())
    		throw new Exception("La transition sp�cifi�e n'est pas une transition");
    	
    	Element modelSource = correspondance.get(source);
    	if(!modelSource.isEtat())
    		throw new Exception("L'�tat source sp�cifi� n'est pas un �tat");
    	    	
    	Element modelDest = correspondance.get(dest);
    	if(!modelDest.isEtat())
    		throw new Exception("L'�tat destination sp�cifi� n'est pas un �tat");
    	
    	/* Check logique */
    	if(modelSource.isEtatPseudoFinal())
    		throw new Exception("Un �tat final ne peut pas �tre utilis� comme �tat source d'une transition");
    	
    	if(modelDest.isEtatPseudoInitial())
    		throw new Exception("Un �tat initial ne peut pas �tre utilis� comme �tat destination d'une transition");
    	
    	
    	
    	/* On ne peut pas lier un �tat initial � un �tat final*/
    	if(modelSource.isEtatPseudoInitial() && modelDest.isEtatPseudoFinal()){
    		throw new Exception("Une transition ne peut pas relier un �tat initial � un �tat final");
    	}
    	
    	
    	/* Converti la transition suivant les nouveaux �tats associ�s */
    	if(modelSource.isEtatPseudoInitial()){
    		if(modelTrans.isTransitionIntermediaire()){
    			modelTrans = new TransitionInitiale((TransitionIntermediaire)modelTrans, (PseudoInitial)modelSource);
    		}
    		else if(modelTrans.isTransitionFinale()){
    			modelTrans = new TransitionInitiale((TransitionFinale)modelTrans, (PseudoInitial)modelSource);
    		}
    	}
    	else if(modelDest.isEtatPseudoFinal()){
    		if(modelTrans.isTransitionInitiale()){
    			modelTrans = new TransitionFinale((TransitionInitiale)modelTrans, (PseudoFinal)modelDest);
    		}
    		else if(modelTrans.isTransitionIntermediaire()){
    			modelTrans = new TransitionFinale((TransitionIntermediaire)modelTrans, (PseudoFinal)modelDest);
    		}
    		
    		((TransitionFinale)modelTrans).setEtiquette(etiquette);
    	}
    	else{ //modelSource : EtatIntermediaire ; modelDest : EtatIntermediaire
    		if(!modelTrans.isTransitionIntermediaire()){
    			modelTrans = new TransitionIntermediaire((TransitionInitiale)modelTrans, (EtatIntermediaire)modelSource);
    		}
    		
    		((TransitionIntermediaire)modelTrans).setEtiquette(etiquette);
    	}
    	this.chercherErreurs();
    }


    public void chercherErreurs(){
    	if(mainConteneur == null)
    		return;
    	
    	//get toutes les erreurs
        erreurs = mainConteneur.chercherErreurs(ihm.getEdGraphique().getZoneErreur());
    	
    	//informe la vue des erreurs
    	if(erreurs.size()>0)
    		erreurs.iterator().next().getObservateur().miseAJour();
    }

    public ArrayList<EtatGraph> getStatesFromSameConteneur(EtatGraph etatGraph) throws Exception {
        Etat e = (Etat)getElementFromGraphic(etatGraph);
        if(e.isEtatPseudoFinal()){
            throw new CantCreateTransitionOnFinal();
        }

        Conteneur c = e.getConteneurParent();
        ArrayList<EtatGraph> result = new ArrayList<EtatGraph>();
        for(Element element : c.getElmts()){
            if(element.isEtatIntermediaire() || (!e.isEtatPseudoInitial() && element.isEtatPseudoFinal()) ){
                result.add((EtatGraph)getEtatGraphFromEtat((Etat)element));
            }
        }
        return result;
    }

    public ArrayList<EtatGraph> getStatesFromSameConteneur(TransitionGraph transitionGraph) throws Exception {
        Transition t = (Transition)getElementFromGraphic(transitionGraph);

        Conteneur c = t.getConteneurParent();
        ArrayList<EtatGraph> result = new ArrayList<EtatGraph>();
        for(Element element : c.getElmts()){
            if(element.isEtatIntermediaire() || element.isEtatPseudoFinal()) {
                result.add((EtatGraph) getEtatGraphFromEtat((Etat) element));
            }
        }
        return result;
    }

    public ArrayList<EtatGraph> getSourceAndDestination(TransitionGraph transitionGraph) throws Exception {
        Transition tg = (Transition)getElementFromGraphic(transitionGraph);

        ArrayList<EtatGraph> sourceAndDestination = new ArrayList<EtatGraph>();

        sourceAndDestination.add(getEtatGraphFromEtat(tg.getEtatSource()));
        sourceAndDestination.add(getEtatGraphFromEtat(tg.getEtatDestination()));


        return sourceAndDestination;
    }

    public String getEtiquette(TransitionGraph transitionGraph) throws Exception {
        Transition tg = (Transition)getElementFromGraphic(transitionGraph);

        return tg.getEtiquette();
    }

    public String getNom(EtatGraph etatGraph) throws Exception {
        Etat e = (Etat)getElementFromGraphic(etatGraph);
        return e.getNom();
    }



    //renvoie tous les �tats simples et composites fils de l'�tatGraph p�re
    /*public HashSet<EtatGraph> getSonFromFatherState(EtatGraph father) throws Exception {
    	HashSet<EtatGraph> states = new HashSet<EtatGraph>();
    	
    	if(father == null || !correspondance.containsKey(father))
    		return states;
    	
    	Element composite = correspondance.get(father);
    	if(!composite.isEtatComposite())
    		return states;
    	
    	HashSet<Element> elmtsFils = ((Composite)composite).getFils().getElmts();
    	for(Element elmt : elmtsFils){
    		if(elmt.isEtat()){
                states.add((EtatGraph)getEtatGraphFromEtat((Etat)elmt));
    		}
    	}
    	
    	return states;
    }*/

    public HashMap<ElementGraphique, Element> getCorrespondance() {
        return correspondance;
    }

    public Conteneur getMainConteneur() {
        return mainConteneur;
    }

    private Element getElementFromGraphic(ElementGraphique eg){
        return correspondance.get(eg);
    }

    private EtatGraph getEtatGraphFromEtat(Etat e) throws Exception {
        ObservateurVue obs = e.getObservateur();
        if(obs instanceof EtatGraph){
            return (EtatGraph)obs;
        } else {
            throw new BadCorrespondanceBetweenObservateurAndSubjectType();
        }
    }

    private ElementGraphique getElemGraphFromElem(Element e) {
        return (ElementGraphique)e.getObservateur();
    }

    public void applatir() throws Exception {
        mainConteneur.applatir();
        Ihm.instance().getEdGraphique().getGraph().refresh();
    }

    public void chargerMainConteneur(Conteneur mainConteneur){
        this.mainConteneur = mainConteneur;
        correspondance.clear();

        ArrayList<Element> listAllElements = new ArrayList<Element>();
        mainConteneur.getAllElements(listAllElements);

      //  HashSet<ElementGraphique> listAllElementsGraphique = new HashSet<ElementGraphique>();
        for(Element e : listAllElements){
            ElementGraphique eg = (ElementGraphique)e.getObservateur();
            correspondance.put(eg,e);
            //listAllElementsGraphique.add(eg);
        }

        this.DEBUG_displayElmts();
        EditeurGraphique.instance().updateListeElementGraphiqueAndDisplay(listAllElements);
        Ihm.instance().getEdGraphique().getGraph().refresh();
        this.chercherErreurs();
    }

    /*  public void chargerMainConteneur(Conteneur mainConteneur){
        this.mainConteneur = mainConteneur;
        correspondance.clear();

        HashSet<Element> listAllElements = mainConteneur.getAllElements();

        HashSet<ElementGraphique> listAllElementsGraphique = new HashSet<ElementGraphique>();
        for(Element e : listAllElements){
            ElementGraphique eg = (ElementGraphique)e.getObservateur();
            correspondance.put(eg,e);
            listAllElementsGraphique.add(eg);
        }

        this.DEBUG_displayElmts();
        EditeurGraphique.instance().updateListeElementGraphiqueAndDisplay(listAllElementsGraphique);
        Ihm.instance().getEdGraphique().getGraph().refresh();
        this.chercherErreurs();
    }*/

    public HashSet<Erreur> getErreurs() {
        return erreurs;
    }
    
    public void propagerErreur(Erreur err){
    	if(err.getObservateur()==null)
    		err.attache(ihm.getEdGraphique().getZoneErreur());
    	
    	erreurs.add(err);
    	err.getObservateur().miseAJour();
    }
}
