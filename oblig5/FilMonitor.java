import java.io.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FilMonitor {

    // Holder alle hashmappene som blir laget via run metoden.
    private Beholder testetPositiv = new Beholder();
    private Beholder testetNegativ = new Beholder();

    // Holder orden paa hvor mange filer totalt som har blitt behandlet.
    private int antallFilerPositiv = 0;
    private int antallFilerNegativ = 0;

    // Holder orden paa hvilke filer som traadene skal hente ut.
    private ArrayList<String> filListe;

    // Teller hvor mange filer som har blitt hentet fra filListe.
    private int filTeller = 0;

    // Holder orden paa hvilket antall filer hver traad skal behandle.
    private int antallTraader;
    private int totaltAntallFiler;
    
    // Lager laaser som hindrer traadene i aa oedelegge for hverandre.
    private Lock hentNavn = new ReentrantLock();
    private Lock settInnPositiv = new ReentrantLock();
    private Lock settInnNegativ = new ReentrantLock();

    public FilMonitor (ArrayList<String> filListe, int antallTraader, int totaltAntallFiler) {
        this.filListe = filListe;
        this.antallTraader = antallTraader;
        this.totaltAntallFiler = totaltAntallFiler;
    }

    // Returnerer en liste med filnavn fra filListe, som hver traad skal behandle individuelt.
    public ArrayList<String> hentFilnavn () {
        
        ArrayList<String> filNavn = new ArrayList<>();

        // Lager en faktor som fordeler et antall fileer aa behandle per traad.
        int ratio = totaltAntallFiler / antallTraader;

        // Hvis faktoren ikke er et heltall maa den oekes med én, ellers hentes det ut mindre
        // enn totalt antall filer.
        if (totaltAntallFiler % antallTraader != 0) {
            ratio ++;
        }

        hentNavn.lock();
        try {
            for (int i = 0; i < ratio; i++) {

                // Soerger for at en traad ikke kan hente ut en fil til hvis filtelleren har naadd
                // maksimum antall filer.
                if (filTeller < totaltAntallFiler) {

                    // Legger en fil til lista som traaden skal bruke i run-metoden.
                    filNavn.add(filListe.get(filTeller));

                    // Oeker antall filer hentet med 1.
                    filTeller++;

                }

            }

        } catch (IndexOutOfBoundsException e) {

        } finally {
            hentNavn.unlock();
        }

        // Returnerer en liste med filnavn som traader skal behandle i run-metoden.
        return filNavn;
        
    }

    // Returnerer beholderen for positivt testet personer.
    public Beholder hentPositivBeholder () {
        return this.testetPositiv;
    }

    // Returnerer beholderen for negativt testet personer.
    public Beholder hentNegativBeholder () {
        return this.testetNegativ;
    }


    // Legger til et hashmap laget av en traad i den negative beholderen.
    public void leggTilPositiv (HashMap<String, SubSekvens> informasjon) {
        
        settInnPositiv.lock();
        try {

            this.testetPositiv.leggTilHashMap(informasjon);
            this.antallFilerPositiv ++;

        } finally {
            settInnPositiv.unlock();
        }
    } 

    // Legger til et hashmap laget av en traad i den negative beholderen.
    public void leggTilNegativ (HashMap<String, SubSekvens> informasjon) {
        
        settInnNegativ.lock();
        try {

            this.testetNegativ.leggTilHashMap(informasjon);
            this.antallFilerNegativ ++;

        } finally {
            settInnNegativ.unlock();
        }
    }

    // Returnerer totalt antall filer som har blitt behandlet.
    public int hentAntallBehandlet () {
        return this.antallFilerPositiv + this.antallFilerNegativ;
    }
}
