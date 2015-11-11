package compresseur;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Thomas on 10/11/15.
 */
public class Rouleau {

    private final List<Pixel> intputData;

    public Rouleau(List<Pixel> pixels) {
        this.intputData = pixels;
    }

    public int compresseur() {
        List<Sequence> sequences = new ArrayList<>();
        int compSize = intputData.get(0).getMaxCompression();
        Sequence sequence = new Sequence(compSize);
        sequence.add(intputData.get(0));
        sequences.add(sequence);

        //MEMOISATION
        int[][] mem = new int[intputData.size()][intputData.size()];
        for (int i = 0; i < intputData.size(); i++) {
            for (int j = 0; j < intputData.size(); j++)
            mem[i][j] = -1;
        }
        int taille = compute(sequence, 1, mem, 0);

        for (int i = 0; i < intputData.size(); i++) {
            for (int j = 0; j < intputData.size(); j++)
                System.out.print(mem[i][j] + " ");
            System.out.println();
        }
        return taille;
    }

    /**
     * retour de la taille minimum pour tout compresser
     *
     * @param s
     * @param idxPixel
     *
     * @return
     */
    private int compute(Sequence s, int idxPixel, int[][] mem, int nbSequence) {
        //cas de base
        if (idxPixel >= intputData.size())
            return s.getSize();
        else {
            if (mem[idxPixel][nbSequence] == -1) {
                int compSize = intputData.get(idxPixel).getMaxCompression();
                int nbSequenceSuiv = nbSequence + 1;
                int newIdx = idxPixel + 1;

                //cas nbPixInSeq == 256
                if (s.isFull()) {
                    Sequence newSequence = new Sequence(compSize);
                    mem[idxPixel][nbSequence] = s.getSize()
                                    + compute(newSequence, newIdx, mem,
                                              nbSequenceSuiv);
                    return mem[idxPixel][nbSequence];
                }

                //cas nouvelle sequence
                Sequence newSequence = new Sequence(compSize);
                newSequence.add(intputData.get(idxPixel));
                int taille = s.getSize() + compute(newSequence, newIdx, mem, nbSequenceSuiv);

                //cas continue sur la meme sequence
                if (compSize < s.getCompressionSize())
                    s.setCompressionSize(compSize);
                s.add(intputData.get(idxPixel));
                int taille1 = compute(s, newIdx, mem, nbSequence);

                mem[idxPixel][nbSequence] = taille <= taille1 ? taille : taille1;
            }
        }
        return mem[idxPixel][nbSequence];
    }

    /**
     * min ( seq(i+1, n+1, d), 11+n(8-d)+ seq(i+1, 0, d)
     *
     * arret return 11+n(8-d)
     */
}
