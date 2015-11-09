package Vues;

import com.mxgraph.model.mxCell;

/**
 * Created by rémy on 06/11/2015.
 */
public class ElementGraphique{
    private ElementGraphique parent = null;
    private mxCell objet_graphique;

    public ElementGraphique(ElementGraphique parent, mxCell objet_graphique) {
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
}
