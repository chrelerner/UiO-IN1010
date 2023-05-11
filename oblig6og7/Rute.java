import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public abstract class Rute extends JButton {
    
    protected Labyrint labyrint;
    protected Tegnepanel tegnepanel;

    // Koordinater.
    protected int x;
    protected int y;

    // Naboruter.
    protected Rute nord;
    protected Rute soer;
    protected Rute oest;
    protected Rute vest;

    public Rute (int x, int y, Labyrint labyrint) {
        this.x = x;
        this.y = y;

        this.labyrint = labyrint;
    }

    // Setter naboene til ruten.
    public void settNaboer (Rute nord, Rute soer, Rute oest, Rute vest) {
        this.nord = nord;
        this.soer = soer;
        this.oest = oest;
        this.vest = vest;
    }

    public int hentKolonne () {
        return this.x;
    }

    public int hentRad () {
        return this.y;
    }

    // Returnerer en karakter som representerer typen rute.
    public abstract char tilTegn ();

    public abstract void gaa (Rute forrige, ArrayList<Tuppel> tupler);

    public void finnUtVei () {

        if (this instanceof SortRute) {
            System.out.println("Error! Sort rute i startposisjon.");
        }
        System.out.println();

        
        this.gaa(null, new ArrayList<Tuppel>());
    }

    // Gir ruta et tegnepanel.
    public void settTegnepanel (Tegnepanel nyttTegnepanel) {
        this.tegnepanel = nyttTegnepanel;
    }

    // initialiserer ruta i GUI format.
    public abstract void initGUI ();


}
