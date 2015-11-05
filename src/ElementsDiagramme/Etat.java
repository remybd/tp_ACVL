package ElementsDiagramme;


/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Etat implements Element {
	private String _nom;
	
	public String getNom(){
		return _nom;
	}
	
	public void setNom(String nom){
		_nom = nom;
	}
}
