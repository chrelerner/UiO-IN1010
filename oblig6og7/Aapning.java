import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;

public class Aapning extends HvitRute {
    
    public Aapning(int x, int y, Labyrint labyrint) {
        super(x, y, labyrint);
    }

    @Override
    public void gaa (Rute forrige, ArrayList<Tuppel> tupler) {
        super.gaa(forrige, tupler);
        if (this.labyrint.hentDetaljert().equals("detaljert")) {
            System.out.println("Funnet aapning paa: " + "(" + this.x + ", " + this.y + ")\n");
        }
        //tupler.add(new Tuppel(this.x, this.y));
        this.labyrint.leggTilUtvei(tupler);
    }
}
