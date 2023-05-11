import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class HvitRute extends Rute {
    
    public HvitRute (int x, int y, Labyrint labyrint) {
        super(x, y, labyrint);
    }

    @Override
    public char tilTegn () {
        return '.';
    }

    @Override
    public void gaa (Rute forrige, ArrayList<Tuppel> tupler) {

        boolean ikkeBesoekt = true;

        // Sjekker om ruta allerede finnes i stien.
        for (int i = 0; i < tupler.size(); i++) {

            // Hvis koordinatene til ruta matcher med koordinater i stien settes ikkeBesoekt til false.
            if ((this.x == tupler.get(i).hentKolonne()) && (this.y == tupler.get(i).hentRad())) {
                ikkeBesoekt = false;
            }
        }

        if (this.labyrint.hentDetaljert().equals("detaljert")) {
            System.out.println("Rute: " + "(" + this.x + ", " + this.y + ")");
        }

        // Legger til ruta i stien.
        tupler.add(new Tuppel(this.x, this.y));

        // Kaller paa nord.
        if (this.nord != null && this.nord != forrige && ikkeBesoekt) {
            ArrayList<Tuppel> nyeTupler1 = new ArrayList<>(tupler);
            this.nord.gaa(this, nyeTupler1);
        }
        // Kaller paa soer.
        if (this.soer != null && this.soer != forrige && ikkeBesoekt) {
            ArrayList<Tuppel> nyeTupler2 = new ArrayList<>(tupler);
            this.soer.gaa(this, nyeTupler2);
        }
        // Kaller paa oest.
        if (this.oest != null && this.oest != forrige && ikkeBesoekt) {
            ArrayList<Tuppel> nyeTupler3 = new ArrayList<>(tupler);
            this.oest.gaa(this, nyeTupler3);
        }
        // Kaller paa vest.
        if (this.vest != null && this.vest != forrige && ikkeBesoekt) {
            ArrayList<Tuppel> nyeTupler4 = new ArrayList<>(tupler);
            this.vest.gaa(this, nyeTupler4);
        }   
    }

    // Initialiserer ruta i GUI format med hvit bakgrunn og sort ramme.
    @Override
    public void initGUI () {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //setFont(new Font("Monospaced", Font.BOLD, 50));
        setPreferredSize(new Dimension(5, 5));
        //setText(" ");
        setOpaque(true);
        setBackground(Color.WHITE);

        Rute denneRuten = this;
        Tegnepanel tegnepanelet = this.tegnepanel;
        class Rutevelger implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                tegnepanelet.trykkRute(denneRuten);
            }
        }
        addActionListener(new Rutevelger());
    }
}
