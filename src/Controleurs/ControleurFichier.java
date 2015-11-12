package Controleurs;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ElementsDiagramme.Conteneur;
import RessourcesExternes.Fichier;
import RessourcesExternes.FichierSauvegarde;

/**
 * Created by r�my on 05/11/2015.
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
    
    private void creerFichier(String path, Conteneur c) throws FileNotFoundException, IOException {
            fichierDeSauvegarde = new FichierSauvegarde(path, c);
    }

    public void chargerFichier(String path) throws FileNotFoundException, ClassNotFoundException, IOException{

        fichierDeSauvegarde = new FichierSauvegarde(path);
        Conteneur mainConteneur = fichierDeSauvegarde.getMainConteneur();
        ControleurDiagramme.instance().chargerMainConteneur(mainConteneur);
    }

    public void sauvegarderFichier(String path) throws FileNotFoundException, IOException{
        Conteneur mainCont = ControleurDiagramme.instance().getMainConteneur();
        if(fichierDeSauvegarde == null)
            creerFichier(path, mainCont);
        
		fichierDeSauvegarde.sauvegarderFichier();
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
