import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

public class Hovedprogram {
    public static void main (String[] args) {
        
        // Klokke som teller tiden hele programmet bruker fra start til slutt.
        long startTid = System.nanoTime(); 

        try {

            File metadata = new File("metadata.csv");
            Scanner lesTilListe = new Scanner(metadata);

            // Lager en liste som holder paa filnavn fra metadata.
            ArrayList<String> metadataListe = new ArrayList<>();

            // Holder styr paa antall filer som finnes i metadata.
            int antallFiler = 0;

            // Soerger for at den foerste linjen i metadata blir ignorert.
            lesTilListe.nextLine();

            // Legger filnavn til metadataListe oeker telleren for antall filer.
            while (lesTilListe.hasNextLine()) {
                metadataListe.add(lesTilListe.nextLine());
                antallFiler ++;
            }

            System.out.println("\nAntall filer: " + antallFiler + ".");

            System.out.print("\nOppgi et antall traader som du oensker skal behandle filene.\n" 
                            + "Hold antallet imellom 1 og " + antallFiler + " traader: ");
            
            Scanner lesInput = new Scanner(System.in);

            // Leser brukerinput for antall traader som skal lese inn filene.
            int antallTraader1 = lesInput.nextInt();
            lesInput.nextLine();

            // Soerger for at brukerinput er innenfor rimelige grenser.
            while (antallTraader1 < 1 || antallFiler < antallTraader1) {

                System.out.print("\nVennligst oppgi et antall imellom 1 og " + antallFiler + " traader: ");
                antallTraader1 = lesInput.nextInt();
                lesInput.nextLine();

            }

            // Lager en monitor som holder rede paa filene og maaten de behandles.
            FilMonitor filMonitor = new FilMonitor(metadataListe, antallTraader1, antallFiler);

            CountDownLatch latch1 = new CountDownLatch(antallTraader1);

            // Teller tiden traadene bruker paa aa lese fra filene.
            long startTidFiler = System.nanoTime();

            // Leser alle filene og skriver fra dem.
            for (int i = 0; i < antallTraader1; i++) {
                Thread traad = new Thread(new FilInnleser(filMonitor, latch1));
                traad.start();
            }

            // Venter paa at alle traadene som leser filene skal bli ferdige.
            try {
                latch1.await();
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            final long SLUTT_TID_FILER = (System.nanoTime() - startTidFiler) / 1000000;

            // Lager variable for hvor mange positivt og negativt testet personer som ble registrert.
            int antallPositivtTestet = filMonitor.hentPositivBeholder().stoerrelse();
            int antallNegativtTestet = filMonitor.hentNegativBeholder().stoerrelse();

            System.out.println("\nAntall filer behandlet: " + filMonitor.hentAntallBehandlet() + ".");

            System.out.print("\nOppgi et antall traader som du oensker skal flette sammen beholderene. \n" 
                                + "Hold antallet over 1: ");

            // Leser brukerinput for hvor mange traader som skal flette hashmappene i beholderene sammen.
            int antallTraader2 = lesInput.nextInt();
            lesInput.nextLine();

            // Soerger for at bruker oppgir minst 2 traader, ettersom programmet krever minst 2 traader
            // for aa flette sammen hashmappene.
            while (antallTraader2 < 2) {

                System.out.print("\nVennligst oppgi et antall over 1: ");
                antallTraader2 = lesInput.nextInt();
                lesInput.nextLine();
            }

            // Lager monitorer som holder rede paa beholderene og maaten de behandles.
            FletterMonitor positivFletterMonitor = new FletterMonitor(filMonitor.hentPositivBeholder());
            FletterMonitor negativFletterMonitor = new FletterMonitor(filMonitor.hentNegativBeholder());
            
            long startTidFletter = 0;

            // Hvis antall traader for aa flette sammen hashmaps er et partall.
            if (antallTraader2 % 2 == 0) {

                CountDownLatch latchPositiv = new CountDownLatch(antallTraader2 / 2);
                CountDownLatch latchNegativ = new CountDownLatch(antallTraader2 / 2);

                // Teller tiden traadene bruker paa aa flette sammen beholderene.
                startTidFletter = System.nanoTime();

                // Starter halvparten av traadene for aa behandle den positive beholderen.
                for (int i = 0; i < antallTraader2 / 2; i++) {
                    Thread traad = new Thread(new BeholderFletter(positivFletterMonitor, latchPositiv));
                    traad.start();
                }

                // Starter den andre halvparten av traadene for aa behandle den negative beholderen.
                for (int i = 0; i < antallTraader2 / 2; i++) {
                    Thread traad = new Thread(new BeholderFletter(negativFletterMonitor, latchNegativ));
                    traad.start();
                }

                try {
                    latchPositiv.await();
                    latchNegativ.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }

            }

            // Hvis antall traader for aa flette sammen hashmaps er et oddetall.
            else if (antallTraader2 % 2 != 0) {
                int positivTraader = antallTraader2 / 2;
                int negativTraader = (antallTraader2 / 2) + 1;

                CountDownLatch latchPositiv = new CountDownLatch(positivTraader);
                CountDownLatch latchNegativ = new CountDownLatch(negativTraader);

                // Teller tiden traadene bruker paa aa flette sammen beholderene.
                startTidFletter = System.nanoTime();

                // Starter halvparten av traadene for aa behandle den positive beholderen.
                for (int i = 0; i < positivTraader; i++) {
                    Thread traad = new Thread(new BeholderFletter(positivFletterMonitor, latchPositiv));
                    traad.start();
                }

                // Starter den andre halvparten av traadene for aa behandle den negative beholderen.
                for (int i = 0; i < negativTraader; i++) {
                    Thread traad = new Thread(new BeholderFletter(negativFletterMonitor, latchNegativ));
                    traad.start();
                }

                try {
                    latchPositiv.await();
                    latchNegativ.await();
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }

            final long SLUTT_TID_FLETTER = (System.nanoTime() - startTidFletter) / 1000000;

            // Lagrer de nye sammenslaatte hashmappene i egne variabler ut fra beholderene.
            HashMap<String, SubSekvens> positivtTestet = filMonitor.hentPositivBeholder().hentHashMap(0);
            HashMap<String, SubSekvens> negativtTestet = filMonitor.hentNegativBeholder().hentHashMap(0);

            // Holder rede paa antall subsekvenser som finner i hver hashmap.
            int positiveSubsekvenser = 0;
            int negativeSubsekvenser = 0;

            for (String x : positivtTestet.keySet()) {
                positiveSubsekvenser ++;
            }

            for (String x : negativtTestet.keySet()) {
                negativeSubsekvenser ++;
            }

            // Printer resultatet til programmet.
            System.out.println("\nAntall positivt testet personer: " + antallPositivtTestet);
            System.out.println("Antall negativt testet personer: " + antallNegativtTestet);
            System.out.println("Antall subsekvenser hos positivt testet personer: " + positiveSubsekvenser);
            System.out.println("Antall subsekvenser hos negativt testet personer: " + negativeSubsekvenser);

            // Lager en liste for dominante subsekvenser, og en teller som holder antallet.
            int antallDominante = 0;
            ArrayList<String> dominanteSubsekvenser = new ArrayList<>();

            // Legger til dominante subsekvenser i lista over, og oeker telleren.
            for (String x : positivtTestet.keySet()) {

                if (positivtTestet.get(x) != null && negativtTestet.get(x) != null) {

                    if (positivtTestet.get(x).hentAntallForekomster() - negativtTestet.get(x).hentAntallForekomster() >= 5) {
                        antallDominante ++;

                        dominanteSubsekvenser.add(x);
                    }
                }
            }


            // Printer resten av resultatet til programet.
            System.out.println("Antall dominante subsekvenser: " + antallDominante);
            System.out.println("\nSubsekvens         Positivt testet            Negativt testet          Differanse\n");

            for (String x : dominanteSubsekvenser) {

                String streng1 = String.format("%23d", positivtTestet.get(x).hentAntallForekomster());
                String streng2 = String.format("%27d", negativtTestet.get(x).hentAntallForekomster());
                String streng3 = String.format("%24d", (positivtTestet.get(x).hentAntallForekomster()-negativtTestet.get(x).hentAntallForekomster()));

                System.out.println(x + streng1 + streng2 + streng3);
            }

            // Lager pene desimaltall for tidene brukt.
            String sluttTidFiler = lagDesimalTall(String.valueOf(SLUTT_TID_FILER));
            String sluttTidFletter = lagDesimalTall(String.valueOf(SLUTT_TID_FLETTER));

            // Printer tidene programmet brukte.
            System.out.print("\nTid brukt paa filinnlesning: " + sluttTidFiler + " sekunder");
            System.out.println("      Tid brukt paa fletting: " + sluttTidFletter + " sekunder");

        } catch (FileNotFoundException e) {
            System.out.println("Kunne ikke aapne metadata.");
        }

        // Variebel med totalt antall sekunder brukt siden start.
        final long TOTAL_TID = (System.nanoTime() - startTid) / 1000000;

        String totalTid = lagDesimalTall(String.valueOf(TOTAL_TID));

        // Printer totalt antall sekunder brukt.
        System.out.println("\nTid brukt siden programstart: " + totalTid + " sekunder");
    }

    // Gjoer om fra millisekunder til sekunder med desimaltall.
    public static String lagDesimalTall (String streng) {

        StringBuilder strengbygger = new StringBuilder(streng);

        if (streng.length() == 3) {
            strengbygger.insert(0, 0);
            strengbygger.insert(1, ".");
        }

        else if (streng.length() == 4) {
            strengbygger.insert(1, ".");
        }

        else if (streng.length() == 5) {
            strengbygger.insert(2, ".");
        }

        else if (streng.length() == 6) {
            strengbygger.insert(3, ".");
        }

        else if (streng.length() == 7) {
            strengbygger.insert(4, ".");
        }

        else if (streng.length() == 8) {
            strengbygger.insert(5, ".");
        }

        else if (streng.length() == 9) {
            strengbygger.insert(6, ".");
        }

        else if (streng.length() == 10) {
            strengbygger.insert(7, ".");
        }

        return strengbygger.toString();
    }
}
