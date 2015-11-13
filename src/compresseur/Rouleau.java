package compresseur;
import java.util.List;
import java.util.Objects;
/**
 * Created by Thomas on 10/11/15.
 */
public class Rouleau {

    private final List<Pixel> intputData;
    static int compteur;
    private Sequence[] chemin;

    public Rouleau(List<Pixel> pixels) {
        this.intputData = pixels;
    }

    public int compresseur(boolean iteratif, boolean display) {
        compteur = 0;
        int taille;
        int[] mem;
        chemin = new Sequence[intputData.size()];
        for (int i = 0; i < intputData.size(); i++) {
            chemin[i] = null;
        }
        if (iteratif){
            mem = new int[intputData.size()+1];
            for (int i = 0; i < intputData.size(); i++) {
                mem[i] = Integer.MAX_VALUE;
            }
            mem[intputData.size()]=0;
            taille = sequenceIteratif(mem, chemin);
        } else {
            mem = new int[intputData.size()];
            for (int i = 0; i < intputData.size(); i++) {
                mem[i] = -1;
            }
            taille = sequence(0, mem, chemin);
        }
        if (display){
            display(mem, intputData.size());
            displayChemin(chemin, 0);
        }
        return taille;
    }

    public void display(int[] mem, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(mem[i] + " ");
        }
    }

    public void display(long[] mem, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(mem[i] + " ");
        }
    }

    private void displayChemin(Sequence[] chemin, int begin) {
        while (begin < intputData.size()){
            System.out.println(
                    "Sequence [pixels: " + chemin[begin].nbPixels + ", comp: " + chemin[begin].compression + "] ");
            begin += chemin[begin].nbPixels;
        }
    }

    private int sequence(int idxPixel, int[] mem, Sequence[] chemin) {
        compteur++;
        if (idxPixel == intputData.size()) {
            return 0;
        } else {
            if (mem[idxPixel] == -1) {
                mem[idxPixel] = Integer.MAX_VALUE;
                int comp = intputData.get(idxPixel).getMaxCompression();
                for (int i = 0; i < 255; ++i) {
                    int t = (i + 1) * (8 - comp) + 11 + sequence(idxPixel + i + 1, mem, chemin);
                    if (t < mem[idxPixel]) {
                        mem[idxPixel] = t;
                        Sequence n = new Sequence();
                        n.nbPixels = i + 1;
                        n.compression = comp;
                        chemin[idxPixel] = n;
                    }
                    //si possible sur le suivant
                    if (idxPixel + i + 1 < intputData.size()) {
                        int newComp = intputData.get(idxPixel + i + 1).getMaxCompression();
                        if (comp > newComp)
                            comp = newComp;
                    } else {
                        break;
                    }
                }
            }
            return mem[idxPixel];
        }
    }

    private int sequenceIteratif(int[] mem, Sequence[] sequence) {
        for (int idxPixel = intputData.size() - 1; idxPixel >= 0; idxPixel--) {
            int d = intputData.get(idxPixel).getMaxCompression();
            for (int j = 0; j < 255; ++j) {
                compteur++;
                int t = 11 + (j + 1) * (8 - d) + mem[idxPixel + j + 1];
                if (t < mem[idxPixel]) {
                    mem[idxPixel] = t;
                    Sequence n = new Sequence();
                    n.nbPixels = j+1;
                    n.compression = d;
                    sequence[idxPixel] = n;
                }
                if (idxPixel + j + 1 < intputData.size()) {
                    if (d > intputData.get(idxPixel + j + 1).getMaxCompression())
                        d = intputData.get(idxPixel + j + 1).getMaxCompression();
                } else
                    break;
            }
        }
        return mem[0];
    }

    public Sequence[] getChemin() {
        return chemin;
    }
}
