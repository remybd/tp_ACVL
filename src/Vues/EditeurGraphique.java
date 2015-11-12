package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.Conteneur;
import ElementsDiagramme.Element;
import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * Created by Jerem on 11/10/2015.
 */

public class EditeurGraphique extends JFrame {

    private final static String etatInitialStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_ELLIPSE;
    private final static String etatFinalStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_DOUBLE_ELLIPSE;

    private static HashMap<mxCell, ElementGraphique> liste_elements_graphiques = new HashMap<>();

    private ZoneErreur zone_erreur = new ZoneErreur();

    final private static EditeurGraphique instanceUnique = new EditeurGraphique();

    Toolkit tool = getToolkit();

    //-------------------------------------------
    //  Partie Barre de menu
    private JMenuBar menu = new JMenuBar();

    private JMenu fichier = new JMenu("Fichier");
    private JMenu aide = new JMenu("Aide");

    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem enregistrer = new JMenuItem("Enregistrer");
    private JMenuItem aplatir = new JMenuItem("Aplatir");
    private JMenuItem fermer = new JMenuItem("Fermer");

    private JMenuItem consulter_manuel = new JMenuItem("Consulter Manuel");
    private JMenuItem a_propos = new JMenuItem("A Propos");

    //Partie gestionnaire de graphe
    //  private ArrayList<mxCell>
    private mxGraph graph = new mxGraph();
    private mxGraphComponent graphComponent = null;

    private JTextField filename = new JTextField();
    private JTextField dir = new JTextField();

    private EditeurGraphique() {
        this.setTitle("Editeur Graphique");
        this.setSize(tool.getScreenSize());

        createMenu();

        JPanel mainPanel = new JPanel();
        JPanel panelGraph = new JPanel(); //Panel

        BorderLayout bl = new BorderLayout();
        BorderLayout b2 = new BorderLayout();

        mainPanel.setLayout(bl);
        panelGraph.setLayout(b2);

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setAutoExtend(false);
        graphComponent.getGraphControl().addMouseListener(new MenuContextuelListener(graphComponent));
        graphComponent.getGraphControl().setSize(this.getSize());
        graphComponent.getGraphHandler().setRemoveCellsFromParent(false);
        graphComponent.setConnectable(false);
        graphComponent.getGraph().setVertexLabelsMovable(false);
        graphComponent.getGraph().setEdgeLabelsMovable(false);
        graphComponent.getGraph().setAllowDanglingEdges(false);
        graphComponent.setExportEnabled(false);
        graphComponent.getGraph().setCellsDisconnectable(false);
        graphComponent.getGraph().setCellsEditable(false);
        graphComponent.setEnterStopsCellEditing(false);
        graphComponent.getGraph().setDropEnabled(false);
        graphComponent.setMaximumSize(new Dimension(400,400));

        panelGraph.setMaximumSize(new Dimension(400, 400));
        panelGraph.add(graphComponent);

        mainPanel.setMaximumSize(new Dimension(400, 400));
        mainPanel.add(panelGraph);
        mainPanel.add(zone_erreur, BorderLayout.NORTH);

        this.setContentPane(mainPanel);
        setVisible(true); //affiche la JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createMenu() {
        // Ajout des sous-items aux items de la barre de menu
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showOpenDialog(EditeurGraphique.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                	File f = c.getSelectedFile();
                    filename.setText(f.getName());
                    dir.setText(f.getAbsolutePath().toString());
					try {
						Ihm.instance().getControleurFichier().chargerFichier(f.getAbsolutePath().toString());
					} catch (ClassNotFoundException
							| IOException e1) {
						new FenetreErreur("Une erreur survenue pendant l'ouverture du fichier ; il n'existe peut-�tre pas.");
					}
					
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    dir.setText("");
                }
            }
        });
        enregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(EditeurGraphique.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                	File f = c.getSelectedFile();
                    filename.setText(f.getName());
                    dir.setText(f.getAbsolutePath().toString());
                    try {
						Ihm.instance().getControleurFichier().sauvegarderFichier(dir.getText());
					} catch (FileNotFoundException e1) {
						new FenetreErreur("Une erreur est survenue pendant la sauvegarde du fichier : il n'existe pas.");
						e1.printStackTrace();
					} catch (IOException e1) {
						new FenetreErreur("Une erreur d'écriture est survenue pendant la sauvegarde du fichier.");
						e1.printStackTrace();
					}
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    dir.setText("");
                }
            }
        });
        aplatir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
                    try {
						Ihm.instance().getControleur().applatir();
					} catch (Exception e1) {
						new FenetreErreur("Une erreur non gérée est survenue pendant l'applatissement.");
						e1.printStackTrace();
					}
                
            }
        });
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.add(aplatir);
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

    public ZoneErreur getZoneErreur(){
        return zone_erreur;
    }

    public EtatGraph ajouterEtatSimple(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_graph;
        EtatGraph eg;
        try {
            etat_graph = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30);
            eg = new EtatGraph(parent,etat_graph, type);
            this.getListe_elements_graphiques().put(etat_graph, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatPseudoInitial(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell = null;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30, etatInitialStyle);
            eg = new EtatGraph(parent, (mxCell) etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatPseudoFinal(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30, etatFinalStyle);
            eg = new EtatGraph(parent,etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatComposite(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30);
            eg = new EtatGraph(parent,etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell,eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public TransitionGraph ajouterTransition(EtatGraph parent, EtatGraph source, EtatGraph destination, String etiquette, EnumTransition type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell transition_mxcell;
        TransitionGraph tg;
        try {
            transition_mxcell = (mxCell)this.getGraphComponent().getGraph().insertEdge(newEtatParent, null, etiquette,
                    (Object)source.getObjet_graphique(),(Object)destination.getObjet_graphique());
            if(parent == null)
                tg = new TransitionGraph(parent, source, destination, transition_mxcell, type);
            else
                tg = new TransitionGraph(parent.getParent(), source, destination, transition_mxcell, type);
            this.getListe_elements_graphiques().put(transition_mxcell,tg);
        } finally {
            graph.getModel().endUpdate();
        }
        return tg;
    }

    public static EditeurGraphique instance() {
        return instanceUnique;
    }

    public HashMap<mxCell, ElementGraphique> getListe_elements_graphiques() {
        return liste_elements_graphiques;
    }

    public void setListe_elements_graphiques(HashMap<mxCell, ElementGraphique> liste_elements_graphiques) {
        this.liste_elements_graphiques = liste_elements_graphiques;
    }

    public ElementGraphique getElement_from_liste(mxCell objet){
        return liste_elements_graphiques.get(objet);
    }

    public mxGraphComponent getGraphComponent(){
        return graphComponent;
    }

    public mxGraph getGraph(){
        return graph;
    }

	public void updateListeElementGraphiqueAndDisplay(
			ArrayList<Element> listAllElements) {
        EditeurGraphique.instance().getGraph().removeCells();

		graph.getModel().beginUpdate();
		try {
			reset();
            ArrayList<ElementGraphique> eg_array = new ArrayList<ElementGraphique>();
			Conteneur top = ControleurDiagramme.instance().getMainConteneur();

			for(Element e : listAllElements) {
				if(e.getConteneurParent().equals(top) || ( ((ElementGraphique) e.getObservateur()).getParent() == null)) {
	                graph.addCell(((ElementGraphique)e.getObservateur()).getObjet_graphique(), Ihm.instance().getEdGraphique().getGraph().getDefaultParent());
				} else {
	                graph.addCell(((ElementGraphique)e.getObservateur()).getObjet_graphique(), ((ElementGraphique)e.getObservateur()).getParent().getObjet_graphique());
				}
                liste_elements_graphiques.put(((ElementGraphique)e.getObservateur()).getObjet_graphique(), (ElementGraphique)e.getObservateur());
			}
		} finally {
            graph.getModel().endUpdate();
		}
	}
    
	public void updateListeElementGraphiqueAndDisplay(
			HashSet<ElementGraphique> listAllElementsGraphique) {
        EditeurGraphique.instance().getGraph().removeCells();

		graph.getModel().beginUpdate();
		try {
			reset();
            ArrayList<ElementGraphique> eg_array = new ArrayList<ElementGraphique>();

			for(ElementGraphique e : listAllElementsGraphique) {
                if(!eg_array.contains(e)) {
                	if(e.getObjet_graphique().isVertex()) {
                        addParent(e,eg_array);
                        if(e.getParent() != null) {
                        	e.getObjet_graphique().setParent((mxICell) graph.getDefaultParent()); 
                        } else {
                        	e.getObjet_graphique().setParent((mxICell) e.getParent().getObjet_graphique()); 
                        }
                        liste_elements_graphiques.put(e.getObjet_graphique(), e);
                	}
                }
			}
			
			for(ElementGraphique e : listAllElementsGraphique) {
                if(!eg_array.contains(e)) {
                    addParent(e,eg_array);
                    if(e.getParent() != null) {
                    	e.getObjet_graphique().setParent((mxICell) graph.getDefaultParent()); 
                    } else {
                    	e.getObjet_graphique().setParent((mxICell) e.getParent().getObjet_graphique()); 
                    }
                    liste_elements_graphiques.put(e.getObjet_graphique(), e);
                }
			}
			
		} finally {
            graph.getModel().endUpdate();
		}
	}

    public Object addParent(ElementGraphique eg, ArrayList<ElementGraphique> eg_array) {
    	Object res = null;
        if(eg != null) {
            if (eg.getParent() != null) {
               Object parent = addParent(eg.getParent(), eg_array);
                res = graph.addCell(eg.getObjet_graphique(), parent);
            } else {
                res = graph.addCell(eg.getObjet_graphique());
            }
            eg_array.add(eg);
        }
        return res;
    }
	
    private void reset() {
        for (Map.Entry<mxCell, ElementGraphique> entry : liste_elements_graphiques.entrySet()) {
        	Object[] tab = {entry.getKey()};
            EditeurGraphique.instance().getGraph().removeCells(tab);
        }
        liste_elements_graphiques = new HashMap<>();
    }
}
