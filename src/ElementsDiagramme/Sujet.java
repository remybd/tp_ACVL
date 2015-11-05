package ElementsDiagramme;

import Vues.Observateur;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Sujet {

    public abstract void attache(Observateur observateur);
    public abstract void detache(Observateur observateur);
    public abstract void informe();
}
