import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

public class Hovedpanel extends JPanel {
    
    Tegnepanel tegnepanel;
    JLabel statustekst;
    JPanel informasjonsPanel;
    JButton velgFil;

    class filVelger implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            
            try {
                trykkVelgFil();
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex);
            }
        }
    }


    public Hovedpanel () throws FileNotFoundException {
        
        // Oppsett av filvelgeren.
        JFileChooser velger = new JFileChooser();
        int resultat = velger.showOpenDialog(null);

        if (resultat != JFileChooser.APPROVE_OPTION) {
            System.exit(1);
        }

        // Lager en labyrint uten detaljert utskrift ut ifra den oppgitte filem.
        Labyrint labyrint = new Labyrint(velger.getSelectedFile(), "nei");

        this.informasjonsPanel = new JPanel();
        this.tegnepanel = new Tegnepanel(this, informasjonsPanel, labyrint);

        setPreferredSize(new Dimension(1250, 750));
        setLayout(new GridLayout(1, 2));

        this.velgFil = velgFil = new JButton("Velg ny fil");
        this.velgFil.addActionListener(new filVelger());
        this.velgFil.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
    }

    public void initGUI () {

        // Lager et separat panel for all informasjonen om labyrinten og utveiene.
        informasjonsPanel.setLayout(new GridLayout(7, 1));
        informasjonsPanel.setFont(new Font("Monospaced", Font.BOLD, 100));

        informasjonsPanel.add(velgFil);

        tegnepanel.initGUI();
        add(tegnepanel);
        statustekst = new JLabel("Trykk paa en hvit rute for aa finne utveier.");
        //add(statustekst);
        //setBackground(Color.BLACK);
        //setOpaque(true);
        

    }

    public JLabel hentStatustekst () {
        return this.statustekst;
    }

    // Velger en ny fil.
    public void trykkVelgFil () throws FileNotFoundException {

        

        // Oppsett av filvelgeren.
        JFileChooser velger = new JFileChooser();
        int resultat = velger.showOpenDialog(null);

        if (resultat != JFileChooser.APPROVE_OPTION) {
            System.exit(2);
        }

        // Lager en labyrint uten detaljert utskrift ut ifra den oppgitte filem.
        Labyrint labyrint = new Labyrint(velger.getSelectedFile(), "nei");

        remove(0);
        remove(0);

        this.informasjonsPanel = new JPanel();
        this.tegnepanel = new Tegnepanel(this, informasjonsPanel, labyrint);
        
        initGUI();
    }
}
