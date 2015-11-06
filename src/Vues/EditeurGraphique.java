package Vues;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import Controleurs.ControleurDiagramme;
import com.mxgraph.view.mxGraph;

/**
 * Created by Jerem on 11/10/2015.
 */

public class EditeurGraphique extends JFrame implements ObservateurVue {

    private ZoneErreur zone_erreur;

    Toolkit tool = getToolkit();

    private mxGraph graph = new mxGraph();
    private ControleurDiagramme controleur = null;

    //  Partie Barre de menu
    private JMenuBar menu = new JMenuBar();

    private JMenu fichier = new JMenu("Fichier");
    private JMenu aide = new JMenu("Aide");

    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem enregistrer = new JMenuItem("Enregistrer");
    private JMenuItem fermer = new JMenuItem("Fermer");

    private JMenuItem consulter_manuel = new JMenuItem("Consulter Manuel");
    private JMenuItem a_propos = new JMenuItem("A Propos");

    public EditeurGraphique(ControleurDiagramme controleur){
        this.controleur = controleur;
        this.setTitle("Editeur Graphique");
        this.setVisible(true);
        this.setSize(tool.getScreenSize());

        // Ajout des sous-items aux items de la barre de menu
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.addSeparator();
        fermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fichier.add(fermer);

        aide.add(consulter_manuel);
        aide.add(a_propos);

        menu.add(fichier);
        menu.add(aide);

        this.setJMenuBar(menu);

        this.addMouseListener(new MenuContextuelListener());

    }

    public void ajoutEtat(){
        CreationEtat window_etat = new CreationEtat(controleur);
        //window_etat.getValider().
    }

    public ControleurDiagramme getControleur(){
        return controleur;
    }

    /*void miseAjour(){

    }*/
}
