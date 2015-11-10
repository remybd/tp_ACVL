package Vues;

import ElementsDiagramme.EnumEtat;

import com.mxgraph.model.mxCell;


/**
 * Created by Jerem on 03/11/2015.
 */
public class EtatGraph extends ElementGraphique {

    private EnumEtat type;

    public EtatGraph(EtatGraph parent, mxCell objet_graphique, EnumEtat type){
        super(parent, objet_graphique);
        this.type = type;
    }

    public EnumEtat getType() {
        return type;
    }

    public void setType(EnumEtat type) {
        this.type = type;
    }

	@Override
	public void miseAJour() {
		// TODO Auto-generated method stub
	}
}
