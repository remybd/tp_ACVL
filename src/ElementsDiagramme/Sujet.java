package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Sujet {

    public abstract void attache(ObservateurVue observateurVue);
    public abstract void detache(ObservateurVue observateurVue);
    public abstract void informe();
}
