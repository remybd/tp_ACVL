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
    
    private void creerFichier(String path, String name, Conteneur c) {
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

    public void chargerFichier(String path, String name){
        try {
        	//si l'extension est spécifiée dans le nom, on la vire
        	int point = name.lastIndexOf('.');
        	if(point>-1 && name.substring(point).equals("."+FichierSauvegarde.FICHIER_EXTENSION)){
        		name = name.substring(0, point);
        	}
        	
            fichierDeSauvegarde = new FichierSauvegarde(name, FichierSauvegarde.FICHIER_EXTENSION, path);
            Conteneur mainConteneur = fichierDeSauvegarde.getMainConteneur();
            ControleurDiagramme.instance().chargerMainConteneur(mainConteneur);
            
		} catch (ClassNotFoundException | IOException e) {
			//TODO : propager l'erreur dans l'IHM
			e.printStackTrace();
		}
    }

    public void sauvegarderFichier(String path, String name){
        Conteneur mainCont = ControleurDiagramme.instance().getMainConteneur();
        if(fichierDeSauvegarde == null)
            creerFichier(name, path, mainCont);
        
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
