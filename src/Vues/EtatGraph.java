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
        if(this.type.equals(EnumEtat.INIT))
            super.supprimable = false;
        else
            super.supprimable = true;
    }

    public EnumEtat getType() {
        return type;
    }

    public void setType(EnumEtat type) {
        this.type = type;
    }

	@Override
	public void miseAJour() {
        //Ihm.instance().getControleur();
	}
}