import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class FilInnleser implements Runnable {
    
    // Monitor som behandler informasjon for filene.
    private FilMonitor filMonitor;

    private CountDownLatch latch;

    public FilInnleser (FilMonitor filMonitor, CountDownLatch latch) {
        
        this.filMonitor = filMonitor;
        this.latch = latch;
    }

    @Override
    // Traadene faar filnavn som skal behandles fra en metode i filmonitoren.
    public void run () {
        
        ArrayList<String> navnListe = filMonitor.hentFilnavn();

        for (String x : navnListe) {

            String[] liste = x.strip().split(",");

            try {

                File fil = new File(liste[0]);

                Scanner scan = new Scanner(fil);

                // Soerger for at alle filene sin foeste linje med "amino_acid" blir ignorert.
                scan.nextLine();

                // Lager et hashmap som tar vare på subsekvensene hos en person.
                HashMap<String, SubSekvens> informasjon = new HashMap<>();

                while (scan.hasNextLine()) {

                    String subsekvenser = scan.nextLine().strip();

                    final int LENGDE = 3;

                    // Legger alle subsekvensene fra strengen inn i informasjon-hashmappet.
                    for (int i = 0; i < subsekvenser.length()-2; i++) {
                        String subsekvens = subsekvenser.substring(i, i + LENGDE);

                        // Soerger for at hver spesifikke subsekvens i en fil bare blir lagt til én gang.
                        if (!informasjon.containsKey(subsekvens) && (subsekvens != null)) {
                            informasjon.put(subsekvens, new SubSekvens(subsekvens));
                        }
                    }

                }

                // Legger hashmappet med subsekvensene i beholderen for positivt testet personer.
                if (liste[1].equals("True")) {
                    filMonitor.leggTilPositiv(informasjon);

                }

                // Legger hashmappet med subsekvensene i beholderen for negativt testet personer.
                else if (liste[1].equals("False")) {
                    filMonitor.leggTilNegativ(informasjon);

                }

            } catch (FileNotFoundException e) {
                System.out.println("Kunne ikke aapne filen: " + liste[0]);
            }

        }

        // Teller ned paa barrieren fra hovedprogrammet.
        latch.countDown();
    }

}
