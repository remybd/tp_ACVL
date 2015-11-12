package Vues;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ElementsDiagramme.EnumEtat;
import Exceptions.NameNotModifiableException;

/**
 * Pour afficher simplement un mesage d'erreur dans une nouvelle fenêtre
 *
 */
public class FenetreErreur extends FenetrePopup implements ActionListener {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 39L;
	
		private JLabel _err;
	    private JButton _ok = new JButton("Ok");

	    public FenetreErreur(String erreur){
	        super();
	       
	        this.setTitle("Erreur");
	        _err = new JLabel(erreur);

	        JPanel centered = new JPanel();
	        centered.add(_err);

	        _ok.addActionListener(this);

	        centered.add(_ok);
	        getPan().add(centered, BorderLayout.CENTER);
	        this.add(getPan(), BorderLayout.CENTER);
	        this.pack();
	    }

	    public void actionPerformed(ActionEvent arg0){
	        this.dispose();
	    }
}
