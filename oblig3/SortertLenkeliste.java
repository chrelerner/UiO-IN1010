public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {
    
    // Legger til elementer i rekkefølge etter deres størrelser.
    @Override
    public void leggTil(T x) {

        Boolean lagtTilSjekk = false;

        // Hvis lista er tom legges verdien x først i lista.
        if (stoerrelse() == 0) {
            forste = new Node(x);
        } else {
            
            // Itererer gjennom lista og sammenlikner elementene
            // med verdien x, og setter x i posisjonen til det foerste
            // elementet som er stoerre enn x. Dytter saa elementene etter x
            // ett hakk bakover.
            inner: for (int i = 0; i < stoerrelse(); i++) {
                if (hent(i).compareTo(x) >= 0) {
                    super.leggTil(i, x);

                    // Hvis et element har blitt lagt til settes denne til true.
                    lagtTilSjekk = true;

                    // Stopper for-loopen og dermed metoden.
                    break inner;
                }
            }

            // Hvis x er stoerre enn alle elementene i lista blir ikke
            // x lagt til via for-loopen. Denne if-testen sjekker om lagtTilSjekk
            // fortsatt er false, og legger isåfall x til slutten av lista.
            if (!lagtTilSjekk) {
                super.leggTil(x);
            }
        }
    }

    // Fjerner det siste elementet i lista. Ettersom leggtil-metoden
    // legger til elementer i rekkefoelge, vil det være det stoerste 
    // elementet som blir fjernet.
    @Override
    public T fjern() throws UgyldigListeIndeks {
        return fjern(stoerrelse()-1);
    }

    @Override
    public void leggTil(int pos, T x) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sett(int pos, T x) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
