import java.util.*;

public class Analyse imlements Runnable {
    
    private AnalyseBuffer analyseBuffer;

    public Analyse (AnalyseBuffer analyseBuffer) {
        this.analyseBuffer = analyseBuffer;
    }

    public void run () {

        Pasient pasient;
        do {
            pasient = this.analyseBuffer.hentUt();

            if (pasient instanceof KvinneligPasient) {
                return Sykehus.kanHaSykdomKvinne(pasient);
            }

            if (pasient instanceof MannligPasient) {
                return Sykehus.kanHaSykdomMann(pasient);
            }
        } while (analysebuffer ikke er tom);
    }
}
