package Erreurs;

import ElementsDiagramme.Etat;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Bloquant extends ErreurEtat{

    public Bloquant(Etat etat, int importance){
        super("Etat Bloquant",etat, importance);
    }
}
