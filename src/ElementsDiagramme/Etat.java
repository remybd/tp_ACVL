package ElementsDiagramme;


/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Etat extends Sujet implements Element {
	private String _nom;
	
	public Etat(String nom){
		this.setNom(nom);
	}
	
	public String getNom(){
		return _nom;
	}
	
	public void setNom(String nom){
		_nom = nom;
	}
}
