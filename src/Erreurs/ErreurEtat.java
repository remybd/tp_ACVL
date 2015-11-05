package Erreurs;

import ElementsDiagramme.Etat;
import ElementsDiagramme.Sujet;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class ErreurEtat extends Erreur{
    private Etat etatParent;

    public ErreurEtat(String nom, Etat etat, int importance){
        super(nom,importance);
        this.etatParent = etatParent;
    }

    public Etat getEtatParent() {
        return etatParent;
    }
}
