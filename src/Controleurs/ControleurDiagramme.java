package Controleurs;

import ElementsDiagramme.*;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;
import Erreurs.Erreur;
import Exceptions.*;
import Vues.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by rémy on 05/11/2015.
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

    //TODO A modifier, ajouterTransition doit recevoir des EtatGraph de la Vue et non pas des états
    public Transition ajouterTransition(EnumTransition type, String etiquette, EtatGraph s, EtatGraph d) throws Exception {
        Etat sEtat = (Etat)getElementFromGraphic(s);
        Etat dEtat = (Etat)getElementFromGraphic(d);

        Conteneur conteneurParent = sEtat.getConteneurParent();

        Transition t = Transition.creerTransition(type,etiquette,sEtat,dEtat,conteneurParent);

        TransitionGraph tg = ihm.createTransitionGraph(s,d,t);
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
            //TODO Exception de cast (l'objet qu'on récupère si le parent != null n'est pas forcément un Composite, je ne sais pas pq)
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



    public void supprimerElement(ElementGraphique elem){
        Element e = getElementFromGraphic(elem);

        for(Element element : e.supprimer()){
            ihm.removeElem(getElemGraphFromElem(element).getObjet_graphique());
            correspondance.remove(elem);
        }
    }



    public void modifierTransition(TransitionGraph transitionGraph, EtatGraph source, EtatGraph dest,String etiquette) throws Exception{
    	/* Check les préconditions */
    	if(!correspondance.containsKey(transitionGraph))
    		throw new Exception("La transition spécifiée n'existe pas");
    	
    	if(!correspondance.containsKey(source))
    		throw new Exception("L'état source spécifié n'existe pas");
    	
    	if(!correspondance.containsKey(dest))
    		throw new Exception("L'état destination spécifié n'existe pas");
    	
    	
    	
    	/* Get les correspondances dans le modèle */
    	Element modelTrans = correspondance.get(transitionGraph);
    	if(!modelTrans.isTransition())
    		throw new Exception("La transition spécifiée n'est pas une transition");
    	
    	Element modelSource = correspondance.get(source);
    	if(!modelSource.isEtat())
    		throw new Exception("L'état source spécifié n'est pas un état");
    	    	
    	Element modelDest = correspondance.get(dest);
    	if(!modelDest.isEtat())
    		throw new Exception("L'état destination spécifié n'est pas un état");
    	
    	/* Check logique */
    	if(modelSource.isEtatPseudoFinal())
    		throw new Exception("Un état final ne peut pas être utilisé comme état source d'une transition");
    	
    	if(modelDest.isEtatPseudoInitial())
    		throw new Exception("Un état initial ne peut pas être utilisé comme état destination d'une transition");
    	
    	
    	
    	/* On ne peut pas lier un état initial à un état final*/
    	if(modelSource.isEtatPseudoInitial() && modelDest.isEtatPseudoFinal()){
    		throw new Exception("Une transition ne peut pas relier un état initial à un état final");
    	}
    	
    	
    	/* Converti la transition suivant les nouveaux états associés */
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


    public HashSet<Erreur> chercherErreurs(){
    	if(mainConteneur == null)
    		return new HashSet<Erreur>();
    	
    	return mainConteneur.chercherErreurs();
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

        sourceAndDestination.add(getEtatGraphFromEtat(tg.getEtatDestination()));
        sourceAndDestination.add(getEtatGraphFromEtat(tg.getEtatSource()));

        return sourceAndDestination;
    }

    //renvoie tous les états simples et composites fils de l'étatGraph père
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

    public Element getElementFromGraphic(ElementGraphique eg){
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
        return (EtatGraph)e.getObservateur();
    }
}
