public class Stabel<T> extends Lenkeliste<T> {
    
    // Legger til et element x i stabelen.
    public void leggPaa(T x) {
        leggTil(x);
    }

    // Fjerner det siste elementet i stabelen,
    // som vil være elementet på posisjon (stoerrelse - 1).
    public T taAv() {
        return fjern(stoerrelse()-1);
    }
}
