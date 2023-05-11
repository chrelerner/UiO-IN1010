import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.*;

public class FletterMonitor {
    
    // Holder rede paa informasjonen for personer.
    private Beholder beholder;

    // Laas som hindrer traadene i aa oedelegge for hverandre.
    private Lock laas = new ReentrantLock();

    public FletterMonitor (Beholder beholder) {
        this.beholder = beholder;
    }
    
    // Returnerer en beholder med to hashmaps fra beholderen.
    public Beholder hentTo () {

        laas.lock();
        try {

            // Henter to hashmaps hvis beholderen har 2 eller fler hashmaps.
            if (beholder.stoerrelse() > 1) {

                Beholder nyBeholder = new Beholder();

                for (int i = 0; i < 2; i++) {
                    nyBeholder.leggTilHashMap(beholder.fjernHashMap(0));
                }
            
                return nyBeholder;
            }

            
        } catch(IndexOutOfBoundsException e) {
        } finally {
            laas.unlock();
        }

        // Hvis traaden har gjort en feil og ikke klarer aa hente to hashmaps returneres null.
        return null;
    }

    // Legger til et flettet hashmap i beholderen.
    public void settInnHashMap (HashMap<String, SubSekvens> hashmap) {

        laas.lock();
        
        try {
            
            beholder.leggTilHashMap(hashmap);

        } finally {
            laas.unlock();
        }
    }

    // Returnerer beholderen.
    public Beholder hentBeholder () {
        return beholder;
    }

}
