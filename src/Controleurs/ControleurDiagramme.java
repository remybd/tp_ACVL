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

    public ControleurDiagramme(Conteneur mainConteneur, Ihm ihm){
        this.mainConteneur = mainConteneur;
        this.ihm = ihm;
    }



    //TODO A modifier, ajouterTransition doit recevoir des EtatGraph de la Vue et non pas des �tats
    public Transition ajouterTransition(EnumTransition type, String etiquette, Etat s, Etat d) throws Exception {
        Transition t = Transition.creerTransition(type,etiquette,s,d);

        TransitionGraph tg = ihm.createTransitionGraph(parent,t);
        t.setObservateur(tg);

        mainConteneur.addElmt(t);
        correspondance.put(tg,t);

        return t;
    }

    public Etat ajouterEtat(EnumEtat type, String nom, EtatGraph parent){
        Etat e = Etat.creerEtat(type,nom,this);

        EtatGraph eg = ihm.createEtatGraph(parent,e);
        e.setObservateur(eg);

        mainConteneur.addElmt(e);
        correspondance.put(eg,e);

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
    	/* Get les correspondances dans le mod�le */
    	if(!correspondance.containsKey(transitionGraph))
    		throw new Exception("La transition sp�cifi�e n'existe pas");
    	
    	if(!correspondance.containsKey(source))
    		throw new Exception("L'�tat source sp�cifi� n'existe pas");
    	
    	if(!correspondance.containsKey(dest))
    		throw new Exception("L'�tat destination sp�cifi� n'existe pas");
    	
    	Element modelTrans = correspondance.get(transitionGraph);
    	if(!modelTrans.isTransition())
    		throw new Exception("La transition sp�cifi�e n'est pas une transition");
    	

    	Element modelSource = correspondance.get(source);
    	if(!modelSource.isEtat())
    		throw new Exception("L'�tat source sp�cifi� n'est pas un �tat");
    	    	
    	Element modelDest = correspondance.get(dest);
    	if(!modelDest.isEtat())
    		throw new Exception("L'�tat destination sp�cifi� n'est pas un �tat");
    	
    	
    	if(modelSource.isEtatPseudoInitial()){
    		
    	}
    	
    	if(modelDest.isEtatPseudoFinal()){
    		
    	}
    	
    	
    }


    public void changerSource(TransitionGraph transitionGraph, EtatGraph source) throws NotASourceException {
        Transition t = (Transition)getElementFromGraphic(transitionGraph);
        Etat e = (Etat)getElementFromGraphic(source);

        if(e instanceof PseudoFinal){
            throw new NotASourceException();
        }

        //TO DO
        if(t instanceof TransitionInitiale){
            PseudoInitial pi = ((TransitionInitiale)(t)).getPseudoInitial();
            pi.setTransition(null);
        } else {
            EtatIntermediaire etatIntermediaire = ((TransitionIntermediaire)(t)).getSource();
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
        Conteneur c = e.getConteneurParent();
        HashSet<EtatGraph> result = new HashSet<EtatGraph>();

        for(Element element : c.getElmts()){
            if(element.isEtat()){
                ObservateurVue obs = element.getObservateur();
                if(obs instanceof EtatGraph){
                    result.add((EtatGraph)obs);
                } else {
                    throw new BadCorrespondanceBetweenObservateurAndSubjectType();
                }
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
    			//TODO : ajouter dans states la correspondance graphique de elmt
                ObservateurVue obs = elmt.getObservateur();
                if(obs instanceof EtatGraph){
                    states.add((EtatGraph)obs);
                } else {
                    throw new BadCorrespondanceBetweenObservateurAndSubjectType();
                }
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
}
