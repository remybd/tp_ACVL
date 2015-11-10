package Controleurs;

import ElementsDiagramme.*;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;
import Erreurs.Erreur;
import Exceptions.*;
import Vues.*;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by r�my on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private Ihm ihm;
    private HashMap<ElementGraphique,Element> correspondance;
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

    }

    //TODO A modifier, ajouterTransition doit recevoir des EtatGraph de la Vue et non pas des �tats
    public Transition ajouterTransition(EnumTransition type, String etiquette, Etat s, Etat d, EtatGraph parent) throws Exception {
        Conteneur conteneurParent;
        if(parent == null){
            conteneurParent = mainConteneur;
        } else {
            conteneurParent = ((Composite)getElementFromGraphic(parent)).getFils();
        }

        Transition t = Transition.creerTransition(type,etiquette,s,d,conteneurParent);

        TransitionGraph tg = ihm.createTransitionGraph(parent,t);
        t.setObservateur(tg);

        conteneurParent.addElmt(t);
        correspondance.put(tg,t);

        return t;
    }

    public Etat ajouterEtat(EnumEtat type, String nom, EtatGraph parent) throws Exception {
        Conteneur conteneurParent;
        if(parent == null){
            conteneurParent = mainConteneur;
        } else {
            conteneurParent = ((Composite)getElementFromGraphic(parent)).getFils();
        }
        Etat e = Etat.creerEtat(type,nom,this,conteneurParent);

        EtatGraph eg = ihm.createEtatGraph(parent,e);
        e.setObservateur(eg);

        conteneurParent.addElmt(e);
        correspondance.put(eg,e);

        if(type == EnumEtat.COMPOSITE){
            PseudoInitial init = (PseudoInitial)this.ajouterEtat(EnumEtat.INIT, nom, eg);
            ((Composite)e).getFils().setPseudoInital(init);

            PseudoInitial psi = ((Composite) e).getFils().getPseudoInitial();
            psi.setConteneurParent(((Composite) e).getFils());
            getEtatGraphFromEtat(psi).setParent(eg);
        }

        return e;
    }

    public void renommerEtat(EtatGraph eg, String nom) throws NameNotModifiableException {
        Etat e = (Etat)getElementFromGraphic(eg);

        if(e.isEtatIntermediaire()){
            e.setNom(nom);
        } else {
            throw new NameNotModifiableException();
        }
    }



    /*public void modifierConteneurParent(EtatGraph eg, ElementGraphique parent) throws Exception {
        Etat e = (Etat)getElementFromGraphic(eg);

        if(!e.isEtatIntermediaire())
            throw new ParentNotModifiableException();
        EtatIntermediaire ei = (EtatIntermediaire)e;

        Element p = getElementFromGraphic(parent);
        Conteneur c;
        if(p instanceof Composite){
            c = ((Composite) p).getFils();
        } else if (p instanceof Conteneur){
            c = (Conteneur) p;
        } else {
            throw new NotAParentException();
        }

        for(Transition t : ei.getDestinations()){
            if(t instanceof TransitionFinale){
                PseudoFinal ef = ((TransitionFinale)(t)).getPseudoFinal();
                ef.resetTransitions();
            } else {
                EtatIntermediaire etatIntermediaire = ((TransitionIntermediaire)(t)).getDestination();
                etatIntermediaire.unLinkDestination(t);
            }
        }
        for(Transition t : ei.getSources()){
            if(t instanceof TransitionInitiale){
                PseudoInitial pi = ((TransitionInitiale)(t)).getPseudoInitial();
                pi.setTransition(null);
            } else {
                EtatIntermediaire etatIntermediaire = ((TransitionIntermediaire)(t)).getSource();
                etatIntermediaire.unLinkSource(t);
            }
        }

        ei.setSources(new HashSet<TransitionIntermediaire>());
        ei.setDestinations(new HashSet<TransitionIntermediaire>());
        c.addElmt(ei);

        //TO DO : enlever l'�tat de son conteneur parent pr�c�dent
    }*/



    public void supprimerElement(ElementGraphique elem){
        Element e = getElementFromGraphic(elem);
        if(e.isEtatIntermediaire()){

        } else if(e instanceof PseudoFinal){
            //TO DO : pas sur de pouvoir faire ce instanceof
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
    }


    public void changerSource(TransitionGraph transitionGraph, EtatGraph source) throws NotASourceException {
        Transition t = (Transition)getElementFromGraphic(transitionGraph);
        Etat e = (Etat)getElementFromGraphic(source);

        if(e instanceof PseudoFinal){
            throw new NotASourceException();
        }

        //TODO
        if(t instanceof TransitionInitiale){
            PseudoInitial pi = ((TransitionInitiale)(t)).getPseudoInitial();
            pi.setTransition(null);
        } else {
            EtatIntermediaire etatIntermediaire = ((TransitionIntermediaire)(t)).getEtatSource();
            etatIntermediaire.unLinkSource(t);
        }
    }

    public void changerDest(TransitionGraph t, EtatGraph dest){

    }

    public void modifierEtiquette(TransitionGraph t){

    }
    
    

    public HashSet<Erreur> chercherErreurs(){
    	if(mainConteneur == null)
    		return new HashSet<Erreur>();
    	
    	return mainConteneur.chercherErreurs();
    }

    public HashSet<EtatGraph> getStatesFromSameConteneur(EtatGraph etatGraph) throws Exception {
        Etat e = (Etat)getElementFromGraphic(etatGraph);
        if(e.isEtatPseudoFinal()){
            throw new CantCreateTransitionOnFinal();
        }

        Conteneur c = e.getConteneurParent();
        HashSet<EtatGraph> result = new HashSet<EtatGraph>();
        for(Element element : c.getElmts()){
            if(element.isEtatIntermediaire() || (!e.isEtatPseudoInitial() && element.isEtatPseudoFinal()) ){
                result.add((EtatGraph)getEtatGraphFromEtat((Etat)element));
            }
        }
        return result;
    }

    //renvoie tous les �tats simples et composites fils de l'�tatGraph p�re
    public HashSet<EtatGraph> getSonFromFatherState(EtatGraph father) throws Exception {
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
    }

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
}
