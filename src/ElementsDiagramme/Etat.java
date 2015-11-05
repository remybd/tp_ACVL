package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Etat extends Sujet implements Element {
	private String _nom;
	
	public Etat(Observateur obs, String nom){
		super(obs);
		this.setNom(nom);
	}
	
	public String getNom(){
		return _nom;
	}
	
	public void setNom(String nom){
		_nom = nom;
	}
}
