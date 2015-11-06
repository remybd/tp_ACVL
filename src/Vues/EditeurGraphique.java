package Vues;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Controleurs.ControleurDiagramme;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * Created by Jerem on 11/10/2015.
 */
public class EditeurGraphique extends JFrame implements Observateur {

	final private static EditeurGraphique instanceUnique = new EditeurGraphique();

    //  Partie Barre de menu
    private JMenuBar menu = new JMenuBar();

    private JMenu fichier = new JMenu("Fichier");
    private JMenu aide = new JMenu("Aide");

    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem enregistrer = new JMenuItem("Enregistrer");
    private JMenuItem fermer = new JMenuItem("Fermer");

    private JMenuItem consulter_manuel = new JMenuItem("Consulter Manuel");
    private JMenuItem a_propos = new JMenuItem("A Propos");
	
	private EditeurGraphique() {		
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
		
		        
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
     
        graph.getModel().beginUpdate();
        try {
          Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
          Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
          graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
          graph.getModel().endUpdate();
        }
     
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
		this.addMouseListener(new MenuContextuelListener());
		
	}
	
	public static EditeurGraphique instance() {
		return instanceUnique;
	}
	
    Toolkit tool = getToolkit();

    private mxGraph graph = new mxGraph();



    public void ajoutEtat(){
      //  CreationEtat window_etat = new CreationEtat();
        //window_etat.getValider().
    }

   /* public ControleurDiagramme getControleur(){
        return controleur;
    }*/

	@Override
	public void miseAJour() {
		// TODO Auto-generated method stub
		
	}
    /*void miseAjour(){

    }*/
}
