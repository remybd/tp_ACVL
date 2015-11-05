package Erreurs;

import ElementsDiagramme.Etat;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class NonUnicite extends ErreurEtat{
    private Etat etatMemeNom;

    public NonUnicite(Etat etat, Etat etatMemeNom, int importance){
        super("Etat Non Unique",etat, importance);
        this.etatMemeNom = etatMemeNom;
    }

    public Etat getEtatMemeNom() {
        return etatMemeNom;
    }
}
