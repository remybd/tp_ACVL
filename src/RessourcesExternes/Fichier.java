package RessourcesExternes;


public class Fichier {
    private String nom;
    private String extension;
    private String chemin;
	
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
