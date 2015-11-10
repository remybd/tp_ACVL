package Controleurs;

import ElementsDiagramme.Conteneur;
import ElementsDiagramme.PseudoInitial;
import RessourcesExternes.Fichier;
import RessourcesExternes.Manuel;

/**
 * Created by rémy on 05/11/2015.
 */
public class ControleurFichier {
    private static ControleurFichier instanceUnique = new ControleurFichier();
    private Fichier fichierDeSauvegarde;
    private Manuel manuel;

    private ControleurFichier(){
        fichierDeSauvegarde = null;
        manuel = new Manuel();
    }

    public static ControleurFichier instance(){
        return instanceUnique;
    }

    public void creerFichier(Conteneur c){

        fichierDeSauvegarde = new Fichier(c,path);
    }

    public void chargerFichier(String path){
        fichierDeSauvegarde = new Fichier(path);
    }

    public void sauvegarderFichier(Conteneur c){
        if(fichierDeSauvegarde != null)
            fichierDeSauvegarde.sauvegarderFichier();
        else {
            creerFichier(c);
        }
    }

    public Manuel getManuel(){
        return manuel;
    }

}
