import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class SortRute extends Rute {
    
    public SortRute (int x, int y, Labyrint labyrint) {
        super(x, y, labyrint);
    }

    @Override
    public char tilTegn () {
        return '#';
    }

    // Gjoer ingen ting siden ruten er sort.
    @Override
    public void gaa (Rute forrige, ArrayList<Tuppel> tupler) {
        ;
    }

    // Initialiserer ruta i GUI format med sort bakgrunn og sort ramme.
    @Override
    public void initGUI () {
        //setPreferredSize(new Dimension(20, 20));
        //setOpaque(true);
        //setBackground(Color.BLACK);
        //setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //setFont(new Font("Monospaced", Font.BOLD, 50));
        setPreferredSize(new Dimension(5, 5));
        //setText(" ");
        setOpaque(true);
        setBackground(Color.BLACK);
    }
}
