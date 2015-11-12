package ElementsDiagramme;

import Controleurs.ControleurDiagramme;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Etat extends Element {
	private String _nom;
	
	public Etat(Conteneur parent, String nom){
		super(parent);
		this.setNom(nom);
	}
	
	public String getNom(){
		return _nom;
	}
	
	public void setNom(String nom){
		_nom = nom;
		informe();
	}

	@Override
	public boolean isTransition() {
		return false;
	}

	@Override
	public boolean isTransitionFinale() {
		return false;
	}

	@Override
	public boolean isTransitionInitiale() {
		return false;
	}

	@Override
	public boolean isTransitionIntermediaire() {
		return false;
	}

	@Override
	public boolean isEtat() {
		return true;
	}
	
	public static Etat creerEtat(EnumEtat type, String nom, ControleurDiagramme c, Conteneur parent) throws Exception {
		Etat e;

		if(type == EnumEtat.COMPOSITE){
			e = new Composite(parent ,"compo_"+nom, new Conteneur());
		}
		else if(type == EnumEtat.INIT){
			e = new PseudoInitial(parent,"init_"+nom);
		}
		else if(type == EnumEtat.SIMPLE){
			e = new Simple(parent,nom);
		}
		else{
			e = new PseudoFinal(parent,"final_"+nom);
		}

		return e;
	}

}
