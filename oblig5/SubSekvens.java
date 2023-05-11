public class SubSekvens {
    private String subsekvens;
    private int antallForekomster;

    public SubSekvens (String subsekvens) {
        this.subsekvens = subsekvens;
        this.antallForekomster = 1;
    }

    public String hentSubsekvens () {
        return subsekvens;
    }

    public void settSubsekvens (String nySubsekvens) {
        this.subsekvens = nySubsekvens;
    }

    public int hentAntallForekomster () {
        return antallForekomster;
    }

    public void settAntallForekomster (int nyttAntall) {
        this.antallForekomster = nyttAntall;
    }
}
