public class Lenkeliste<T> implements Liste<T> {

    // Lager en klasse node som bare kan bli 
    // sett og håndtert av klassen lenkeliste.
    class Node {
        Node neste;
        T data;

        public Node (T data) {
            this.data = data;
        }
    }

    protected Node forste;

    // Returnerer stoerrelsen på lenkelista.
    public int stoerrelse() {
        int stoerrelse = 0;
        Node peker = forste;

        while (peker != null) {
            stoerrelse += 1;
            peker = peker.neste;
        }

        return stoerrelse;
    }

    // Legger til verdien x i posisjon pos, og dytter
    // alle resterende verdier ett hakk bakover.
    public void leggTil(int pos, T x) throws UgyldigListeIndeks {
        Node peker1 = forste;
        Node peker2 = forste;
        if (pos < 0 || pos > stoerrelse()) {
            throw new UgyldigListeIndeks(pos);

        } else if (pos == 0) {
            forste = new Node(x);
            forste.neste = peker1;

        } else {
            // Setter peker1 til noden i posisjon pos-1 i lenkelista.
            for (int i = 0; i < pos-1; i++) {
                peker1 = peker1.neste;
            }

            // Setter peker2 til noden i posisjon pos i lenkelista.
            for (int i = 0; i < pos; i++) {
                peker2 = peker2.neste;
            }

            // Setter peker1 sin neste til å være en ny node med data x.
            peker1.neste = new Node(x);

            // Setter den nye noden sin neste til peker2. 
            peker1.neste.neste = peker2;
        }
    }

    // Legger til x.
    public void leggTil(T x) {

        // Setter forste til x hvis forste ikke eksisterer.
        if (stoerrelse() == 0) {
            forste = new Node(x);
        } 
        
        // Lager en peker som settes til den siste noden,
        // og setter denne nodens neste node til x.
        else {
            Node peker = forste;

            while (peker.neste != null) {
                peker = peker.neste;
            }
        
            peker.neste = new Node(x);
        }
    }

    // Erstatter verdien i posisjon pos med en ny verdi x.
    public void sett(int pos, T x) throws UgyldigListeIndeks {
        Node peker1 = forste;
        Node peker2 = forste;
        Node nyNode = new Node(x);

        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeIndeks(pos);

        } else if (pos == 0) {
            forste = new Node(x);
            forste.neste = peker1.neste;

        } else {
            // Setter peker1 til noden i posisjon pos-1 i lenkelista.
            for (int i = 0; i < pos-1; i++) {
                peker1 = peker1.neste;
            }

            // Setter peker2 til noden i posisjon pos i lenkelista.
            for (int i = 0; i < pos; i++) {
                peker2 = peker2.neste;
            }

            // Noden i posisjon pos-1 får den nye noden som sin neste.
            peker1.neste = nyNode;

            // Den nye noden i posisjon pos får den forrige noden sin neste, som sin neste.
            nyNode.neste = peker2.neste;
        }
    }

    // Returnerer verdien i posisjon pos.
    public T hent(int pos) throws UgyldigListeIndeks {
        Node peker = forste;

        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeIndeks(-1);

        } if (pos == 0) {
            return forste.data;

        } else {
            for (int i = 0; i < pos; i++) {
                peker = peker.neste;
            }

            return peker.data;
        }
    }

    // Fjerner og returnerer verdien i posisjon pos, og setter alle
    // resterende noder ett hakk framover.
    public T fjern(int pos) throws UgyldigListeIndeks {
        Node peker1 = forste;
        Node peker2 = forste;
        if (pos < 0 || pos >= stoerrelse()) {
            throw new UgyldigListeIndeks(pos);

        } else if (pos == 0) {
            forste = peker1.neste;
            return peker1.data;

        } else {
            // Setter peker1 til noden i posisjon pos-1 i lenkelista.
            for (int i = 0; i < pos-1; i++) {
                peker1 = peker1.neste;
            }

            // Setter peker2 til noden i posisjon pos i lenkelista.
            for (int i = 0; i < pos; i++) {
                peker2 = peker2.neste;
            }

            peker1.neste = peker2.neste;

            return peker2.data;
        }
    }

    // Fjerner og returnerer det første elementet i lista.
    public T fjern() throws UgyldigListeIndeks {
        if (stoerrelse() == 0) {
            throw new UgyldigListeIndeks(-1);
        } else {
            T data = forste.data;
            forste = forste.neste;
            return data;
        }
    }

    @Override
    public String toString() {
        Node peker = forste;
        String pekerString = "";

        while (peker.neste != null) {
            pekerString = pekerString + ", " + peker.data;
            peker = peker.neste;
        }

        return pekerString;
    }
}