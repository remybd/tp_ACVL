package Vues;

import com.mxgraph.model.mxCell;

/**
 * Created by r�my on 06/11/2015.
 */
public abstract class ElementGraphique implements ObservateurVue {

	private EtatGraph parent = null;
    private mxCell objet_graphique;
    protected boolean supprimable;

    public ElementGraphique(EtatGraph parent, mxCell objet_graphique) {
        this.parent = parent;
        this.objet_graphique = objet_graphique;
    }

    public String getNom(){
        return (String)this.objet_graphique.getValue();
    }

    public void setNom(String nom){
        this.objet_graphique.setValue((Object) nom);
    }

    public mxCell getObjet_graphique(){
        return objet_graphique;
    }

    public void setObjet_Graphique(mxCell objet_graphique){
        this.objet_graphique = objet_graphique;
    }
    
    public EtatGraph getParent() {
 		return parent;
 	}

 	public void setParent(EtatGraph parent) {
 		this.parent = parent;
    	this.getObjet_graphique().setParent(parent.getObjet_graphique());
 	}

    private boolean isSupprimable(){
        return supprimable;
    }
}