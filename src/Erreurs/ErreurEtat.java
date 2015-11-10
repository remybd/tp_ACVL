package Erreurs;

import ElementsDiagramme.Etat;
import ElementsDiagramme.Sujet;

/**
 * Created by rémy on 05/11/2015.
 */
public class ErreurEtat extends Erreur{
    private Etat etatParent;

    public ErreurEtat(String err, Etat etat, int importance){
        super(err,importance);
        this.etatParent = etatParent;
    }

    public Etat getEtatParent() {
        return etatParent;
    }
}
