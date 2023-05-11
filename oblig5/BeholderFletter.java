import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.*;


public class BeholderFletter implements Runnable {
    
    // Monitor som holder rede paa flettingen av hashmaps.
    private FletterMonitor fletterMonitor;

    private CountDownLatch latch;

    public BeholderFletter (FletterMonitor fletterMonitor, CountDownLatch latch) {
        this.fletterMonitor = fletterMonitor;
        this.latch = latch;
    }

    @Override
    public void run () {

        // Saa lenge beholderen har mer enn en hashmap vil traaden proeve aa flette to nye hashmaps.
        outer: while (fletterMonitor.hentBeholder().stoerrelse() >= 2) {

            Beholder beholder1 = fletterMonitor.hentTo();

            // Soerger for a stoppe while-loopen hvis en traad har hentet ut et hashmap som ikke finnes.
            if (beholder1 == null) {
                break outer;
            }

            HashMap<String, SubSekvens> info = Beholder.slaaSammenHashMap(beholder1.hentHashMap(0), beholder1.hentHashMap(1));

            // Sender det sammenslaatte hashmappet tilbake til beholderen.
            fletterMonitor.settInnHashMap(info);

        }

        // Teller ned paa barrieren fra hovedprogrammet.
        latch.countDown();
    }
}
