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

    public Transition ajouterTransition(EnumTransition type, String etiquette, Etat s, Etat d) throws Exception {
        Transition t;

        //TO DO : peut être modifier les constructeurs pour ne pas avoir à mettre null pour l'Observateur
        if(type == EnumTransition.INTER){
            if(!s.isEtatIntermediaire() || !d.isEtatIntermediaire())
                throw new NoIntermediaryStateException();

            t = new TransitionIntermediaire(null,etiquette,(EtatIntermediaire)s,(EtatIntermediaire)d);
        }
        else if(type == EnumTransition.FINAL){
            if(!s.isEtatIntermediaire() || !(d instanceof PseudoFinal))
                throw new NoIntermediaryAndFinalStateException();
            //TO DO : peut être modifier le constructeur pour avoir à remplir les états
            t = new TransitionFinale(null,etiquette);
        }
        else{//transition initiale
            if(!(s instanceof PseudoInitial) || !d.isEtatIntermediaire())
                throw new NoIntermediaryAndInitialStateException();
            //TO DO : peut être modifier le constructeur pour avoir à remplir l'état de destination ?
            t = new TransitionInitiale(null,(PseudoInitial)s);
        }

        TransitionGraph tg = ihm.createTransitionGraph(t);
        t.setObservateur(tg);

        mainConteneur.addElmt(t);
        correspondance.put(tg,t);

        return t;
    }


    public Etat ajouterEtat(EnumEtat type, String nom){
        Etat e;

        //TO DO : peut être modifier les constructeurs pour ne pas avoir à mettre null pour l'Observateur
        if(type == EnumEtat.COMPOSITE){
            PseudoInitial init = (PseudoInitial)ajouterEtat(EnumEtat.INIT,nom);
            e = new Composite(null,nom, new Conteneur(init));
        }
        else if(type == EnumEtat.INIT){
            e = new PseudoInitial(null,"init_"+nom);
        }
        else if(type == EnumEtat.SIMPLE){
            e = new Simple(null,nom);
        }
        else{//état final
            //TO DO : peut être modifier le constructeur pour ne pas avoir à mettre null pour la transition
            e = new PseudoFinal(null,"final_"+nom,null);
        }

        EtatGraph eg = ihm.createEtatGraph(e);
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



    public void modifierConteneurParent(EtatGraph eg, ElementGraphique parent) throws Exception {
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

        //TO DO : enlever les transitions de leurs destinations et sources
        for(Transition t : ei.getDestinations()){
            if(t instanceof TransitionFinale){
                PseudoFinal ef = ((TransitionFinale)(t)).getPseudoFinal();
                ef.setTransition(null);
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
    }



    public void supprimerElement(ElementGraphique elem){
        Element e = getElementFromGraphic(elem);
        if(e.isEtatIntermediaire()){

        } else if(e instanceof PseudoFinal){
            //TO DO : pas sur de pouvoir faire ce instanceof
        }
    }

    public void changerSource(TransitionGraph t, EtatGraph source){

    }

    public void changerDest(TransitionGraph t, EtatGraph dest){

    }

    public void modifierEtiquette(TransitionGraph t){

    }

    public HashSet<Erreur> chercherErreurs(){

        return new HashSet<Erreur>();
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
