package RessourcesExternes;

import ElementsDiagramme.Conteneur;

import java.io.*;

/**
 * Created by rémy on 05/11/2015.
 */
public class FichierSauvegarde extends Fichier{
	public static final String FICHIER_EXTENSION = "diag";
	
    private Conteneur mainConteneur;

    //création d'un fichier
    public FichierSauvegarde(String nom, String extension, String chemin, Conteneur mainConteneur) throws FileNotFoundException, IOException{
        super(nom, extension, chemin);

        this.mainConteneur = mainConteneur;
        this.sauvegarderFichier();
    }
    
    //création d'un fichier
    public FichierSauvegarde(String chemin, Conteneur mainConteneur) throws FileNotFoundException, IOException{
        super(chemin);

        this.mainConteneur = mainConteneur;
        this.sauvegarderFichier();
    }
    
    //chargement d'un fichier
    public FichierSauvegarde(String nom, String extension, String chemin) throws FileNotFoundException, IOException, ClassNotFoundException{
        super(nom, extension, chemin);

        this.mainConteneur = this.chargerFichier();
    }

    //ouverture d'un fichier
    public FichierSauvegarde(String path) throws ClassNotFoundException, IOException{
        super(path);
        
        this.mainConteneur = chargerFichier();
    }

    public void sauvegarderFichier() throws FileNotFoundException, IOException{
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(this.getCheminRelatif()))));

            //écriture du mainconteneur dans le fichier
            oos.writeObject(mainConteneur);

        }finally{
        	if(oos!=null)
        		oos.close();
        }
    }

    public Conteneur getMainConteneur() {
        return mainConteneur;
    }

    
    /**
     * Lit le fichier et en retourne le conteneur
     * @return
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public Conteneur chargerFichier() throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(this.getCheminRelatif());
		ObjectInputStream ois= new ObjectInputStream(fis);
		try {	
			mainConteneur = (Conteneur) ois.readObject(); 
		} finally {
			try {
				ois.close();
			} finally {
				fis.close();
			}
		}
		
		return mainConteneur;
	}
}
