package Vues;

import ElementsDiagramme.EnumTransition;

/**
 * Created by Jerem on 03/11/2015.
 */
public class EtatGraph extends ElementGraphique {

    private EnumEtat type;

    public EtatGraph(EnumEtat type){
        super(null, null);
        this.type = type;
    }

    public EnumEtat getType() {
        return type;
    }

    public void setType(EnumEtat type) {
        this.type = type;
    }
}
