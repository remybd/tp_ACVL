package Erreurs;

import ElementsDiagramme.Etat;
import ElementsDiagramme.Sujet;

/**
 * Created by r�my on 05/11/2015.
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
