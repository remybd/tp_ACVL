package Controleurs;

import Vues.Ihm;

/**
 * Created by rémy on 10/11/2015.
 */
public class DiagrammeSequenceMain {

    public static void main(String[] args){
        try {
            ControleurDiagramme con = ControleurDiagramme.instance();
            ControleurFichier fich = ControleurFichier.instance();
            con.init();
            Ihm.instance().setControleur(con);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
