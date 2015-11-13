package compresseur;
import compresseur.Pixel;
import compresseur.Rouleau;
import org.junit.Test;
import reader.RawReader;
import util.ImgMultiplier;
import util.RawWriter;
import util.SegWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas on 10/11/15.
 */
public class RouleauTest {

    Rouleau rouleau;
    String [] allFiles = {"Baboon", "Barbara", "Goldhill", "Lena", "Peppers"};

    @Test
    public void testCompression0() {
        /*
            Image : 00011111 00111111 00011111
            Meilleur taille : 1 sequence avec 3 pixels comp
            ressés à 2 == 11 + 3*6 = 29
         */
        List<Pixel> ps = new ArrayList<>();
        byte b = 31;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = 63;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = 31;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 29, rouleau.compresseur(true, false));
        assertEquals("Taille min must be", 29, rouleau.compresseur(false, false));
    }

    @Test
    public void testCompression1(){
        List<Pixel>ps = new ArrayList<>();
        byte b = (byte) 255;
        Pixel p = new Pixel(b);
        ps.add(p);
        for (int i = 0; i < 8; ++i){
            byte b1 = 1;
            Pixel p1 = new Pixel(b1);
            ps.add(p1);
        }
        byte b2 = (byte) 255;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 57, rouleau.compresseur(true, false));
        assertEquals("Taille min must be", 57, rouleau.compresseur(false, false));
    }

    @Test
    public void testCompression2() {
        /*
            Image : 00000001 00000000 00000000
            Meilleur taille : 1 sequence avec 3 pixels comp
            ressés à 7 == 11 + 3*1 = 14
         */
        List<Pixel> ps = new ArrayList<>();
        byte b = 1;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = 0;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = 0;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 14, rouleau.compresseur(true, false));
        assertEquals("Taille min must be", 14, rouleau.compresseur(false, false));
    }

    @Test
    public void testCompression3(){
        List<Pixel> ps = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            byte b = 31; //00011111
            Pixel p = new Pixel(b);
            ps.add(p);
            //11+100*5 = 511
        }
        for (int i = 0; i < 100; i++) {
            byte b = 63; //00111111
            Pixel p = new Pixel(b);
            ps.add(p);
            //11+100*6 = 611
        }
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 1122, rouleau.compresseur(true, false));
        assertEquals("Taille min must be", 1122, rouleau.compresseur(false, false));
    }

    @Test
    public void testCompressionBaboon() throws IOException {
        RawReader r = new RawReader("Baboon.raw");
        List<Pixel> ps = r.buildList();
        rouleau = new Rouleau(ps);
        rouleau.compresseur(false, false);
        System.out.println(Rouleau.compteur);
    }

    @Test
    public void testAllPicture() throws IOException {
        //recursive
        long [] recursiveResult = new long [allFiles.length];
        int i = 0;
        for(String s : allFiles){
            RawReader r = new RawReader(s);
            rouleau = new Rouleau(r.buildList());
            long start = System.currentTimeMillis();
            rouleau.compresseur(false, false);
            long time = System.currentTimeMillis() - start;

            recursiveResult[i++]=time;
        }
        long [] iterativeResult = new long [allFiles.length];
        i = 0;
        for(String s : allFiles){
            RawReader r = new RawReader(s+".raw");
            rouleau = new Rouleau(r.buildList());
            long start = System.currentTimeMillis();
            rouleau.compresseur(true, false);
            long time = System.currentTimeMillis() - start;
            iterativeResult[i++]=time;
        }
        rouleau.display(recursiveResult, recursiveResult.length);
        System.out.println();
        rouleau.display(iterativeResult, iterativeResult.length);

    }

    @Test
    public void testProcess() throws IOException {
        File file = new File("Lena.raw");
        long sizeOriginal = file.length();
        RawReader reader = new RawReader("Lena.raw");
        reader.read(true);
        Rouleau r = new Rouleau(reader.buildList());
        r.compresseur(true, true);
        SegWriter w = new SegWriter("Lena.seg", reader.getData());
        w.write(r.getChemin());
        file = new File("Lena.seg");
        long sizeCompression = file.length();
        float ratioCompression = ((float)sizeCompression)/sizeOriginal;
        System.out.println(ratioCompression);
    }

    @Test
    public void computeRatio() throws IOException{
        for(String sfile : allFiles){
            File file = new File(sfile+".raw");
            long sizeOriginal = file.length();
            RawReader reader = new RawReader(sfile+".raw");
            reader.read(true);
            Rouleau r = new Rouleau(reader.buildList());
            r.compresseur(true, false);
            SegWriter w = new SegWriter(sfile+".seg", reader.getData());
            w.write(r.getChemin());
            file = new File(sfile+".seg");
            long sizeCompression = file.length();
            float ratioCompression = ((float)sizeCompression)/sizeOriginal;
            System.out.println(ratioCompression);
        }
    }

    @Test
    public void testProcessBIG() throws IOException {
    	RawReader reader = new RawReader("Lena.raw");
    	reader.read(false);
    	ImgMultiplier multiplier = new ImgMultiplier(reader.getData());
    	byte[] big = multiplier.grow();
    	RawWriter rawWriter = new RawWriter("Lena_g.raw");
    	rawWriter.write(big);
    	long orig = new File("Lena_g.raw").length();
    	reader = new RawReader("Lena_g.raw");
    	reader.read(true);
    	Rouleau r = new Rouleau(reader.buildList());
        r.compresseur(true, false);
        SegWriter w = new SegWriter("Lena_g.seg", reader.getData());
        w.write(r.getChemin());
        long target = new File("Lena_g.seg").length();
        double ratio = ((double)target)/orig;
        System.out.println(ratio);
    }
}