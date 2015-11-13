package compresseur;
import compresseur.Pixel;
import compresseur.Rouleau;
import org.junit.Test;
import reader.RawReader;
import util.ImgMultiplier;
import util.RawWriter;
import util.SegWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    
    @Test
    public void testAllBIG() throws IOException {
    	Map<String, String> files = new HashMap<>();
    	files.put("Lena.raw", "Lena_g");
    	files.put("Baboon.raw", "Baboon_g");
    	files.put("Barbara.raw", "Barbara_g");
    	files.put("Goldhill.raw", "Goldhill_g");
    	files.put("Peppers.raw", "Peppers_g");
    	List<Double> resultats = new ArrayList<>(files.size());
    	List<Long> temps = new ArrayList<>(files.size());
    	for(Entry<String, String> e : files.entrySet()) {
    		long orig = expand(e.getKey());
    		RawReader reader = new RawReader(e.getValue()+".raw");
    		reader.read(true);
    		Rouleau r = new Rouleau(reader.buildList());
            long start = System.currentTimeMillis();
    		r.compresseur(false, false);
            long end = System.currentTimeMillis();
            SegWriter w = new SegWriter(e.getValue()+".seg", reader.getData());
    		w.write(r.getChemin());
    		long compress = new File(e.getValue()+".seg").length();
    		resultats.add(((double)compress)/orig);
    		temps.add(end-start);
    	}
    	FileWriter fw = new FileWriter("resultats.txt");
    	for(double d : resultats) {
    		fw.write(d+";");
    	}
    	fw.flush();
    	fw.close();
    	fw = new FileWriter("temps.txt");
    	for(long t : temps) {
    		fw.write(t+";");
    	}
    	fw.flush();
    	fw.close();
    }
    
    private static long expand(String path) throws IOException {
    	RawReader reader = new RawReader(path);
    	reader.read(false);
    	ImgMultiplier mult = new ImgMultiplier(reader.getData());
    	byte[] res = mult.grow();
    	String[] seg = path.split("\\.");
    	StringBuilder sb = new StringBuilder(seg[0]);
    	for(int i = 1; i < seg.length - 2; i++) {
    		sb.append(seg[i]);
    	}
    	sb.append("_g");
    	sb.append("."+seg[seg.length-1]);
    	RawWriter writer = new RawWriter(sb.toString());
    	writer.write(res);
    	return new File(sb.toString()).length();
    }
}