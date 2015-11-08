package Controleurs;

import ElementsDiagramme.*;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;
import Erreurs.Erreur;
import Exceptions.NoIntermediaryAndFinalStateException;
import Exceptions.NoIntermediaryAndInitialStateException;
import Exceptions.NoIntermediaryStateException;
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


    public void renommerEtat(EtatGraph eg, String nom){
    }

    public void modifierConteneurParent(EtatGraph eg, EtatGraph parent){

    }

    public void supprimerElement(ElementGraphique e){

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
}
