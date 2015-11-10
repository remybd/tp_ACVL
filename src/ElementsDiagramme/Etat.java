package ElementsDiagramme;

import Controleurs.ControleurDiagramme;
import Vues.EtatGraph;
import Vues.ObservateurVue;

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
	}

	@Override
	public boolean isTransition() {
		return false;
	}

	@Override
	public boolean isEtat() {
		return true;
	}
	
	public static Etat creerEtat(EnumEtat type, String nom, ControleurDiagramme c){
		Etat e;

		//TO DO : peut être modifier les constructeurs pour ne pas avoir à mettre null pour l'Observateur
		if(type == EnumEtat.COMPOSITE){
			PseudoInitial init = (PseudoInitial)c.ajouterEtat(EnumEtat.INIT,nom);
			e = new Composite(null,nom, new Conteneur(init));
		}
		else if(type == EnumEtat.INIT){
			e = new PseudoInitial(null,"init_"+nom);
		}
		else if(type == EnumEtat.SIMPLE){
			e = new Simple(null,nom);
		}
		else{//état final
			//TO DO : peut être modifier le constructeur pour ne pas avoir à mettre null pour la transition
			e = new PseudoFinal(null,"final_"+nom,null);
		}

		return e;
	}

}
