package Vues;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rémy on 06/11/2015.
 */
public class EditionTransition extends FenetrePopup implements ActionListener{

    private JComboBox liste_etats_source = new JComboBox();
    private JComboBox liste_etats_destination = new JComboBox();
    private JLabel liste_label_source = new JLabel("Liste des etats source");
    private JLabel liste_label_destination = new JLabel("Liste des etats de destination");

    private JLabel etiquette_label = new JLabel("Etiquette");
    private JTextField etiquette_transition = new JTextField();
    private JButton valider = new JButton("Valider");

    private JPanel line_1 = new JPanel();
    private JPanel line_2 = new JPanel();
    private JPanel line_3 = new JPanel();
    private JPanel line_4 = new JPanel();


    public EditionTransition(String etiquette){
        super();
        //etiquette_transition.setPreferredSize(new Dimension(150, 30));

        line_1.setLayout(new BoxLayout(line_1, BoxLayout.LINE_AXIS));
        line_1.add(liste_label_source);
        line_1.add(liste_etats_source);

        line_2.setLayout(new BoxLayout(line_2, BoxLayout.LINE_AXIS));
        line_2.add(liste_label_destination);
        line_2.add(liste_etats_destination);

        line_3.setLayout(new BoxLayout(line_3, BoxLayout.LINE_AXIS));
        line_3.add(etiquette_label);
        etiquette_transition.setText(etiquette);
        line_3.add(etiquette_transition);
        this.valider.addActionListener(this);

        line_4.setLayout(new BoxLayout(line_4, BoxLayout.LINE_AXIS));
        line_4.add(valider);

        getPan().setLayout(new BoxLayout(getPan(), BoxLayout.PAGE_AXIS));
        getPan().add(line_1);
        getPan().add(line_2);
        getPan().add(line_3);
        getPan().add(line_4);

        this.add(getPan());
        this.pack();
    }

    public void actionPerformed(ActionEvent arg0){
        Ihm.instance().getControleur().create_transition(, this.type);
        this.dispose();
    }

}
