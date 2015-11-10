package Controleurs;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import ElementsDiagramme.Conteneur;
import RessourcesExternes.Fichier;
import RessourcesExternes.FichierSauvegarde;

/**
 * Created by rémy on 05/11/2015.
 */
public class ControleurFichier {
    private FichierSauvegarde fichierDeSauvegarde;
    private Fichier manuel = new Fichier("userGuide","pdf","");

    public void creerFichier(String name, String path, Conteneur c){
    	fichierDeSauvegarde = new FichierSauvegarde(name,FichierSauvegarde.FICHIER_EXTENSION, path, c);
    }

    public Conteneur chargerFichier(String path){ 	
        try {
			return fichierDeSauvegarde.chargerFichier();
		} catch (ClassNotFoundException | IOException e) {
			//TODO : propager l'erreur dans l'IHM
			e.printStackTrace();
			return null;
		}
    }

    public void sauvegarderFichier(Conteneur mainCont){
        if(fichierDeSauvegarde == null)
            creerFichier("new_diag", "", mainCont);
        
        try {
			fichierDeSauvegarde.sauvegarderFichier();
		} catch (IOException e) {
			// TODO propager l'erreur dans l'IHM
			e.printStackTrace();
		}
    }

    public Fichier getManuel(){
        return manuel;
    }
    
    public void ouvrirManuel(){
    	try {
			Desktop.getDesktop().open(new File(getManuel().getCheminRelatif()));
		} catch (IOException e) {
			//TODO : propager l'erreur dans l'IHM
			e.printStackTrace();
		}
    }
}
