package RessourcesExternes;


public class Fichier {
    private String nom;
    private String extension;
    private String chemin;
	
    /**
     * 
     * @param chemin : le chemin complet du fichier, incluant nom et (optionnel) l'extension, ou juste le nom
     */
    public Fichier (String chemin){
        int point = chemin.lastIndexOf('.');
        if(point<0){
        	this.setExtension(FichierSauvegarde.FICHIER_EXTENSION);
        	point = chemin.length();
        }
        else
        	this.setExtension(chemin.substring(point+1, chemin.length()));
        
        int slash = chemin.lastIndexOf('/');
        if(slash<0){
        	this.setNom(chemin.substring(0, point));
        	this.setChemin("");
        }
        else{
        	this.setNom(chemin.substring(slash+1, point));
        	this.setChemin(chemin.substring(0, slash));        	
        }
    }
    
    public Fichier (String nom, String extension, String chemin){
        this.setNom(nom);
        this.setExtension(extension);
        this.setChemin(chemin);
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		if(chemin.charAt(chemin.length()-1) != '/')
			chemin += '/';
		
		this.chemin = chemin;
	}

	public String getCheminAbsolu(){
		return getChemin()+getNom()+"."+getExtension();
	}
	
	public String getCheminRelatif(){
		return getNom()+"."+getExtension();
	}
}
