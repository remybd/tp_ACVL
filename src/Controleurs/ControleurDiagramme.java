package Controleurs;

import ElementsDiagramme.Conteneur;
import ElementsDiagramme.Element;

import java.util.HashMap;

/**
 * Created by r�my on 05/11/2015.
 */
public class ControleurDiagramme {
    private Conteneur mainConteneur;
    private HashMap<ElementGraphique,Element> correspondance;

    public ControleurDiagramme(Conteneur mainConteneur){
        this.mainConteneur = mainConteneur;
    }




}
