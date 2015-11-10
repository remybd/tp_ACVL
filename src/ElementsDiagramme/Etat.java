package ElementsDiagramme;

import Controleurs.ControleurDiagramme;
import Vues.EtatGraph;
import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Etat extends Sujet implements Element {
	private String _nom;
	
	public Etat(ObservateurVue obs, String nom){
		super(obs);
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

	public static Etat creerEtat(EnumEtat type, String nom, ControleurDiagramme c){
		Etat e;

		//TO DO : peut �tre modifier les constructeurs pour ne pas avoir � mettre null pour l'Observateur
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
		else{//�tat final
			//TO DO : peut �tre modifier le constructeur pour ne pas avoir � mettre null pour la transition
			e = new PseudoFinal(null,"final_"+nom,null);
		}

		return e;
	}

}
