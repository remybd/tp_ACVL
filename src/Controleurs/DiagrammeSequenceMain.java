package Controleurs;

import Vues.Ihm;

/**
 * Created by r�my on 10/11/2015.
 */
public class DiagrammeSequenceMain {

    public static void main(String[] args){
        try {
            ControleurDiagramme con = ControleurDiagramme.instance();
            ControleurFichier con_fichier = ControleurFichier.instance();
            con.init();
            Ihm.instance().setControleur(con);
            Ihm.instance().setControleurFichier(con_fichier);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
