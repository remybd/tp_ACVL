package Vues;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
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
	
    
    //Partie gestionnaire de graphe
  //  private ArrayList<mxCell> 
	private mxGraph graph = new mxGraph();

    
	private EditeurGraphique() {
		this.setTitle("Editeur Graphique");
		this.setVisible(true);
		this.setSize(tool.getScreenSize());
		
		createMenu();
				
        JPanel mainPanel = new JPanel(); //Panel

        FlowLayout bl = new FlowLayout(FlowLayout.CENTER);   //layoutManager
        mainPanel.setLayout(bl);    //attache le layoutManager au panel           

        JLabel  lab=new JLabel("Bonjour le monde!!");  //créé un label
        mainPanel.add(lab);  //l'ajoute au panel       
        
        Object parent = graph.getDefaultParent();
        Object v1 = null;
        mxCell vertex = null;
        graph.getModel().beginUpdate();
        try {
        	
    		mxGeometry geometry = new mxGeometry(20, 20, 80, 30);
    		geometry.setRelative(false);

    		vertex = new mxCell("Hello", geometry, null);
    		vertex.setId(null);
    		vertex.setVertex(true);
    		vertex.setConnectable(true);
    		graph.addCell(vertex, parent);
    		System.out.println(vertex.isVertex());
        	
        	
        	 v1 = (Object) vertex;
       //   Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
          
          Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
          graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
          graph.getModel().endUpdate();
        }
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
       // graph.getModel().get
      //  graphComponent.getCellAt(MouseEvent.gCursor.getDefaultCursor().g, arg1)
        graphComponent.getGraphControl().addMouseListener(new MenuContextuelListener(graphComponent));
   //     graphComponent.getComponentAt(System, arg1)
     //   graphComponent.getComponents()[0]..addMouseListener(new MenuContextuelListener(graphComponent.getComponent(0)));
 ///       graphComponent.getGraphControl().getComponentListeners()[0].
   //     graphComponent.
        System.out.println("hsit" + graphComponent.getCellAt(19,19));
        System.out.println("hzesit");
        System.out.println(graphComponent.getCellAt(20,20).equals(vertex) + "HOURAAAA");
        mainPanel.add(graphComponent);
        
        setContentPane(mainPanel);  //defini le panel de la JFrame
        setVisible(true);  //affiche la JFrame
		
	    setSize(400, 320);
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void createMenu() {
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
	}
	
	public static EditeurGraphique instance() {
		return instanceUnique;
	}
	
    Toolkit tool = getToolkit();   


    public void ajoutEtat(){
      //  CreationEtat window_etat = new CreationEtat();
        //window_etat.getValider().
    	//graph.
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
	
	/*	JPanel mainPanel = new JPanel();
	  mainPanel.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });*/
	  
	//  this.add(mainPanel);
/*     mxGraph graph = new mxGraph();
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
//  graphComponent.addMouseListener(new MenuContextuelListener());
  graphComponent.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });
  graphComponent.setBackground(Color.BLACK);
  graphComponent.setSize(400, 320);
  graphComponent.setVisible(true);
  getContentPane().add(graphComponent);
	this.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });
  graphComponent.repaint();
  graphComponent.
  LayoutManager.pack();
*/
}
