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
 * Created by rémy on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private Ihm ihm;
    private HashMap<ElementGraphique,Element> correspondance;

    public ControleurDiagramme(Conteneur mainConteneur, Ihm ihm){
        this.mainConteneur = mainConteneur;
        this.ihm = ihm;
    }



    //TODO A modifier, ajouterTransition doit recevoir des EtatGraph de la Vue et non pas des états
    public Transition ajouterTransition(EnumTransition type, String etiquette, Etat s, Etat d, EtatGraph parent) throws Exception {
        Transition t = Transition.creerTransition(type,etiquette,s,d);

        TransitionGraph tg = ihm.createTransitionGraph(t);
        t.setObservateur(tg);

        mainConteneur.addElmt(t);
        correspondance.put(tg,t);

        return t;
    }

    public Etat ajouterEtat(EnumEtat type, String nom, EtatGraph parent){
        Etat e = Etat.creerEtat(type,nom,this);

        EtatGraph eg = ihm.createEtatGraph(e,parent);
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

        //TO DO : enlever l'état de son conteneur parent précédent
    }*/



    public void supprimerElement(ElementGraphique elem){
        Element e = getElementFromGraphic(elem);
        if(e.isEtatIntermediaire()){

        } else if(e instanceof PseudoFinal){
            //TO DO : pas sur de pouvoir faire ce instanceof
        }
    }



    public void modifierTransition(TransitionGraph transitionGraph, EtatGraph source, EtatGraph dest,String etiquette){

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

    public HashSet<EtatGraph> getStatesFromSameConteneur(EtatGraph etatGraph){

    }

    //renvoie tous les états simples et composites fils de l'étatGraph père
    public HashSet<EtatGraph> getSonFromFatherState(EtatGraph father){

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
