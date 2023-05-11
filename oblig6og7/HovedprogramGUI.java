import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.util.*;

public class HovedprogramGUI {
    public static void main (String[] args) throws FileNotFoundException {
        JFrame vindu = new JFrame();
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //vindu.getContentPane().setLayout(new FlowLayout());

        Hovedpanel hovedpanel = new Hovedpanel();
        hovedpanel.initGUI();
        //vindu.getContentPane().add(hovedpanel);
        vindu.add(hovedpanel);

        vindu.pack();

        vindu.setVisible(true);
    }
}

// For rutene initialiserer du de bare en gang. Du kan endre fargene via setBackground(nyFarge).
// Du trenger et antall ruter inne i hvert panel, saa du faar orden. Rutene er bare knapper, alt du trenger
// aa gjoere er aa endre paa det som trengs aa endres paa naar du trykker paa en knapp. Endre info om utveier
// fargen paa rutene osv.
