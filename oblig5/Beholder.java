import java.util.ArrayList;
import java.util.HashMap;

public class Beholder {
    private ArrayList<HashMap<String, SubSekvens>> beholder = new ArrayList<>();

    // Legger til hashmappet i beholderen.
    public void leggTilHashMap (HashMap<String, SubSekvens> hashMap) {
        beholder.add(hashMap);
    }

    // Returnerer og fjerner hashmappet fra oppgitt posisjon i beholderen.
    public HashMap<String, SubSekvens> fjernHashMap (int indeks) {
        return beholder.remove(indeks);
    }

    // Returnerer hashmappet fra oppgitt posisjon i beholderen.
    public HashMap<String, SubSekvens> hentHashMap (int indeks) {
        return beholder.get(indeks);
    }

    // Returnerer antall hashmaps i beholderen.
    public int stoerrelse () {
        return beholder.size();
    }

    // Slaar sammen de to hashmappene til et hashmap.
    public static HashMap<String, SubSekvens> slaaSammenHashMap (HashMap<String, SubSekvens> a, HashMap<String, SubSekvens> b) {

        HashMap<String, SubSekvens> nyHashMap = new HashMap<>();

        // Overfoerer alle elementene fra a til det nye hashmappet.
        for (String key : a.keySet()) {
            nyHashMap.put(key, a.get(key));
        }

        // Overfoerer alle elementene fra b til det nye hashmappet.
        // Hvis en noekkel fra b allerede finnes i det nye hashmappet, oekes antallet
        // forekomster for subsekvensen til noekkelen i det nye hashmappet.
        for (String key: b.keySet()) {

            // Hvis noekkelen ikke allerede finnes i det nye hashmappet
            // legges elementet til på vanlig vis.
            if (!nyHashMap.containsKey(key)) {
                nyHashMap.putIfAbsent(key, b.get(key));
            }  

            // Hvis noekkelen finnes i hashmappet summeres antallet subsekvensene
            // forekommer i de to subsekvens-objektene.
            else {
                nyHashMap.get(key).settAntallForekomster(nyHashMap.get(key).hentAntallForekomster()+b.get(key).hentAntallForekomster());
            }

        }
        
        return nyHashMap;
    }
}
