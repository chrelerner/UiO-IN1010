public class Tuppel {
    
    private int x;
    private int y;

    public Tuppel (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hentKolonne () {
        return x;
    }

    public int hentRad () {
        return y;
    }

    @Override
    public String toString () {
        return "(" + x + ", " + y + ")";
    }

}
