package RessourcesExternes;

import ElementsDiagramme.Conteneur;

import java.io.*;

/**
 * Created by rémy on 05/11/2015.
 */
public class Fichier {
    private String nom;
    private String extension;
    private String chemin;
    private Conteneur mainConteneur;

    public Fichier (String nom, String extension, String chemin, Conteneur mainConteneur){
        this.nom = nom;
        this.extension = extension;
        this.chemin = chemin;
        this.mainConteneur = mainConteneur;
    }

    public void sauvegarderFichier(){
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(chemin+nom+extension))));

            //écriture du mainconteneur dans le fichier
            oos.writeObject(mainConteneur);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
