package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Etat;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Bloquant extends ErreurEtat{

    public Bloquant(Etat etat, int importance, ObservateurVue zoneErreur){
        super("Etat Bloquant",etat, importance, zoneErreur);
    }
}
