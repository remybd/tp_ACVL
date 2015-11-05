package RessourcesExternes;

/**
 * Created by rémy on 05/11/2015.
 */
public class Fichier {
    private String nom;
    private String extension;
    private String chemin;
    private Conteneneur maintConteneur;

    public Fichier (String nom, String extension, String chemin, String mainConteneur){
        this.nom = nom;
        this.extension = extension;
        this.chemin = chemin;
        this.maintConteneur = maintConteneur;
    }
}
