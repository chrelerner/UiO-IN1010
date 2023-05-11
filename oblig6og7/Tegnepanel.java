import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class Tegnepanel extends JPanel {
    
    private Hovedpanel  hovedpanel;
    private Labyrint labyrint;

    // Inneholder all informasjonen som skal vises om labyrintens status.
    JPanel informasjonsPanel;

    // Informasjon om labyrinten som vises i et separat panel.
    private JLabel stoerrelsePaaLabyrint;
    private JLabel valgtRute;
    private JLabel antallUtveier;
    private JLabel antallRuterKortestUtvei;
    private JLabel antallRuterLengstUtvei;
    private JLabel antallRuterDeltUtvei;

    public Tegnepanel (Hovedpanel hovedpanel, JPanel informasjonsPanel, Labyrint labyrint) {
        this.hovedpanel = hovedpanel;
        this.labyrint = labyrint;
        this.informasjonsPanel = informasjonsPanel;
    }

    public void initGUI () {
        setLayout(new GridLayout(labyrint.hentAntallRader(), labyrint.hentAntallKolonner()));

        for (int y = 0; y < labyrint.hentAntallRader(); y ++) {
            for (int x = 0; x < labyrint.hentAntallKolonner(); x ++) {
                
                Rute rute = labyrint.hentRutenett()[y][x];

                rute.settTegnepanel(this);

                rute.initGUI();

                add(rute);
            }
        }

        setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));

        stoerrelsePaaLabyrint = new JLabel("                              Antall kolonner: " + labyrint.hentAntallKolonner() + "     Antall kolonner: " 
                                            + labyrint.hentAntallKolonner() + "     Antall ruter: " + labyrint.hentAntallHviteRuter());
        valgtRute = new JLabel("                              Valgt rute: ");
        antallUtveier = new JLabel("                              Antall utveier: ");
        antallRuterKortestUtvei = new JLabel("                              Antall ruter i kortest utvei: ");
        antallRuterLengstUtvei = new JLabel("                              Antall ruter i lengst utvei: ");
        antallRuterDeltUtvei = new JLabel("                              Antall ruter i delt utvei: ");

        stoerrelsePaaLabyrint.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        valgtRute.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        antallUtveier.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        antallRuterKortestUtvei.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        antallRuterLengstUtvei.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));
        antallRuterDeltUtvei.setBorder(BorderFactory.createLineBorder(Color.WHITE, 20));

        informasjonsPanel.add(stoerrelsePaaLabyrint);
        informasjonsPanel.add(valgtRute);
        informasjonsPanel.add(antallUtveier);
        informasjonsPanel.add(antallRuterKortestUtvei);
        informasjonsPanel.add(antallRuterLengstUtvei);
        informasjonsPanel.add(antallRuterDeltUtvei);
        hovedpanel.add(informasjonsPanel);
        
    }

    // Alt som skal skje naar du trykker paa en rute.
    public void trykkRute (Rute rute) {

        ArrayList<ArrayList<Tuppel>> utveier = labyrint.finnUtveiFra(rute.hentKolonne(), rute.hentRad());

        for (int y = 0; y < labyrint.hentAntallRader(); y ++) {
            for (int x = 0; x < labyrint.hentAntallKolonner(); x ++) {
                
                Rute rute1 = labyrint.hentRutenett()[y][x];

                outer: if (rute1 instanceof HvitRute) {
                    // Sjekker om ruta finnes i begge utveiene. Gjoer isaafall ruta cyan.
                    if (finnesIUtvei(labyrint.hentKortestUtvei(), rute1) && finnesIUtvei(labyrint.hentLengstUtvei(), rute1)) {
                        rute1.setBackground(Color.CYAN);
                        break outer;
                    }
                    // Sjekker om ruta finnes i kortest utvei. Gjoer isaafall ruta groenn.
                    if (finnesIUtvei(labyrint.hentKortestUtvei(), rute1)) {
                        rute1.setBackground(Color.GREEN);
                        break outer;
                    }
                    // Sjekker om ruta finnes i lengst utvei. Gjoer isaafall ruta roed.
                    if (finnesIUtvei(labyrint.hentLengstUtvei(), rute1)) {
                        rute1.setBackground(Color.BLUE);
                        break outer;
                    }
                    else {
                        rute1.setBackground(Color.WHITE);
                    }
                }

                if (rute1 == rute) {
                    rute1.setBackground(Color.RED);
                }
            }
        }
        
        valgtRute.setText("                              Valgt rute: " + "(" + rute.hentKolonne() + ", " + rute.hentRad() + ")          Roed farge");
        antallUtveier.setText("                              Antall utveier: " + labyrint.hentAntallUtveier());
        antallRuterKortestUtvei.setText("                              Antall ruter i kortest utvei: " + labyrint.hentKortestUtvei().size() + "          Groenn farge");
        antallRuterLengstUtvei.setText("                              Antall ruter i lengst utvei: " + labyrint.hentLengstUtvei().size() + "          Blaa farge");
        antallRuterDeltUtvei.setText("                              Antall ruter i delt utvei: " + labyrint.hentAntallRuterDeltUtvei() + "          Turkis farge");

    }

    public boolean finnesIUtvei (ArrayList<Tuppel> utvei, Rute rute) {
        for (int i = 0; i < utvei.size(); i++) {

            // Hvis koordinatene til ruta matcher med koordinater i stien settes ikkeBesoekt til false.
            if ((rute.hentKolonne() == utvei.get(i).hentKolonne()) && (rute.hentRad() == utvei.get(i).hentRad())) {
                return true;
            }
        }
        return false;
    }

}
