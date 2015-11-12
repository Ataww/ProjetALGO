package compresseur;
import compresseur.Pixel;
import compresseur.Rouleau;
import org.junit.Test;
import reader.RawReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas on 10/11/15.
 */
public class RouleauTest {

    Rouleau rouleau;

    @Test
    public void testCompression0() {
        /*
            Image : 00011111 00111111 00011111
            Meilleur taille : 1 sequence avec 3 pixels comp
            ressés à 2 == 11 + 3*6 = 29
         */
        List<Pixel> ps = new ArrayList<>();
        byte b = 31 - 127;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = 63 - 127;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = 31 - 127;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 29, rouleau.compresseur(true, false));
        assertEquals("Taille min must be", 29, rouleau.compresseur(false, false));
    }

    @Test
    public void testCompression1(){
        List<Pixel>ps = new ArrayList<>();
        byte b = 127;
        Pixel p = new Pixel(b);
        ps.add(p);
        for (int i = 0; i < 8; ++i){
            byte b1 = 1 - 127 ;
            Pixel p1 = new Pixel(b1);
            ps.add(p1);
        }
        byte b2 = 127;
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
        byte b = 1 - 127;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = -127;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = -127;
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
            byte b = 31-127; //00011111
            Pixel p = new Pixel(b);
            ps.add(p);
            //11+100*5 = 511
        }
        for (int i = 0; i < 100; i++) {
            byte b = 63-127; //00111111
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
        byte[] data = r.getData();
        List<Pixel> ps = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            Pixel p = new Pixel(data[i]);
            ps.add(p);
        }
        rouleau = new Rouleau(ps);
        rouleau.compresseur(false, false);
        System.out.println(Rouleau.compteur);
    }

    @Test
    public void testAllPicture() throws IOException {
        String [] allFiles = {"Baboon.raw", "Barbara.raw", "Goldhill.raw", "Lena.raw", "Peppers"};
    }
}