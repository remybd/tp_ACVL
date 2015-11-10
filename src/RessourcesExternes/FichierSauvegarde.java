package RessourcesExternes;

import ElementsDiagramme.Conteneur;

import java.io.*;

/**
 * Created by rémy on 05/11/2015.
 */
public class FichierSauvegarde extends Fichier{
	public static final String FICHIER_EXTENSION = "diag";
	
    private Conteneur mainConteneur;

    public FichierSauvegarde (String nom, String extension, String chemin, Conteneur mainConteneur){
        super(nom, extension, chemin);
        this.mainConteneur = mainConteneur;
    }

    public void sauvegarderFichier() throws FileNotFoundException, IOException{
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(getChemin()+getNom()+getExtension()))));

            //écriture du mainconteneur dans le fichier
            oos.writeObject(mainConteneur);
        } finally{
        	if(oos != null)
        		oos.close();
        }
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
