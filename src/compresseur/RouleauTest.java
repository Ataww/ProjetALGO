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
    public void testInitialCompress() {
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
        assertEquals("Taille min must be", 29, rouleau.compresseur());
    }

    @Test
    public void testInitialCompress2() {
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
        assertEquals("Taille min must be", 14, rouleau.compresseur());
    }

    @Test
    public void testSmallPicCompress(){
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
        assertEquals("Taille min must be", 57, rouleau.compresseur());
    }

    @Test
    public void testCompression() throws IOException {
        RawReader r = new RawReader("Baboon.raw");
        byte[] data = r.getData();
        List<Pixel> ps = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            Pixel p = new Pixel(data[i]);
            ps.add(p);
        }
        rouleau = new Rouleau(ps);
        rouleau.compresseur();
        System.out.println(Rouleau.compteur);
    }

    ////var pix []int = []int{255,1,255,1,255,1,255,1,255,1} // 91
    //var pix []int = []int{1,2,5,30,29,28,31,2,2,1} // 61
    //var pix []int =[]int{1,1} // 13
    //var pix []int = []int{255,1,1,1,1,1,1,1,1,255} // 57
    @Test
    public void test1(){
        byte b = 127;
        byte b1 = -126;
        Pixel p = new Pixel(b);
        Pixel p1= new Pixel(b1);
        Pixel p2= new Pixel(b);
        Pixel p3= new Pixel(b1);
        Pixel p4= new Pixel(b);
        Pixel p5= new Pixel(b1);
        Pixel p6= new Pixel(b);
        Pixel p7= new Pixel(b1);
        Pixel p8= new Pixel(b);
        Pixel p9= new Pixel(b1);
        List<Pixel> ps = new ArrayList<>();
        ps.add(p);ps.add(p1);ps.add(p2);ps.add(p3);ps.add(p4);ps.add(p5);ps.add(p6);ps.add(p7);ps.add(p8);ps.add(p9);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 91, rouleau.compresseur());
    }

    @Test
    public void test2(){
        byte b = 1 - 127;
        Pixel p = new Pixel(b);
        b = 2 - 127;
        Pixel p1= new Pixel(b);
        b = 5 - 127;
        Pixel p2= new Pixel(b);
        b=  30 - 127;
        Pixel p3= new Pixel(b);
        b = 29 - 127;
        Pixel p4= new Pixel(b);
        b = 28-127;
        Pixel p5= new Pixel(b);
        b = 31 - 127;
        Pixel p6= new Pixel(b);
        b = 2 - 127;
        Pixel p7= new Pixel(b);
        b = 2 - 127;
        Pixel p8= new Pixel(b);
        b = 1 - 127;
        Pixel p9= new Pixel(b);
        List<Pixel> ps = new ArrayList<>();
        ps.add(p);ps.add(p1);ps.add(p2);ps.add(p3);ps.add(p4);ps.add(p5);ps.add(p6);ps.add(p7);ps.add(p8);ps.add(p9);
        rouleau = new Rouleau(ps);
        assertEquals("Taille min must be", 61, rouleau.compresseur());
    }
}