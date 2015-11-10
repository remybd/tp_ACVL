package RessourcesExternes;

import ElementsDiagramme.Conteneur;

import java.io.*;

/**
 * Created by rémy on 05/11/2015.
 */
public class Fichier {
    private String path = "";
    private String extension = ".diag";
    private Conteneur mainConteneur;

    //création d'un fichier
    public Fichier (Conteneur mainConteneur){
        this.mainConteneur = mainConteneur;


        sauvegarderFichier();
    }

    //ouverture d'un fichier
    public Fichier (String path){
        this.path = path;
        ObjectInputStream ois;
        try {
            //On récupère les données du fichier sérialisé
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    new File(path))));

            try {
                mainConteneur = (Conteneur)ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sauvegarderFichier(){
        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(path))));

            //écriture du mainconteneur dans le fichier
            oos.writeObject(mainConteneur);
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Conteneur getMainConteneur() {
        return mainConteneur;
    }
}
