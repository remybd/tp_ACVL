package Controleurs;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ElementsDiagramme.Conteneur;
import RessourcesExternes.Fichier;
import RessourcesExternes.FichierSauvegarde;

/**
 * Created by rémy on 05/11/2015.
 */
public class ControleurFichier {
	private static ControleurFichier instanceUnique = new ControleurFichier();
    private FichierSauvegarde fichierDeSauvegarde = null;
    private Fichier manuel = new Fichier("userGuide","pdf","");

    private ControleurFichier(){
    	
    }

    public static ControleurFichier instance(){
        return instanceUnique;
    }
    
    public void creerFichier(String name, String path, Conteneur c) {
        try {
            fichierDeSauvegarde = new FichierSauvegarde(name, FichierSauvegarde.FICHIER_EXTENSION, path, c);
        } catch (FileNotFoundException e) {
            // TODO : propager l'erreur dans l'IHM
            e.printStackTrace();
        } catch (IOException e) {
            // TODO : propager l'erreur dans l'IHM
            e.printStackTrace();
        }
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

    public void sauvegarderFichier(String path){
        Conteneur mainCont = ControleurDiagramme.instance().getMainConteneur();
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
