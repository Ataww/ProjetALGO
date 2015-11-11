package compresseur;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by Thomas on 10/11/15.
 */
public class RouleauTest {

    Rouleau r;

    @Test
    public void testInitialCompress(){
        /*
            Image : 00011111 00111111 00011111
            Meilleur taille : 1 sequence avec 3 pixels comp
            ressés à 2 == 11 + 3*6 = 29
         */
        List<Pixel>ps = new ArrayList<>();
        byte b = 31-127;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = 63-127;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = 31-127;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        r = new Rouleau(ps);
        assertEquals("Taille min must be", 29, r.compresseur());
    }

    @Test
    public void testInitialCompress2(){
        /*
            Image : 00000001 00000000 00000000
            Meilleur taille : 1 sequence avec 3 pixels comp
            ressés à 7 == 11 + 3*1 = 14
         */
        List<Pixel>ps = new ArrayList<>();
        byte b = 1-127;
        Pixel p = new Pixel(b);
        ps.add(p);
        byte b1 = -127;
        Pixel p1 = new Pixel(b1);
        ps.add(p1);
        byte b2 = -127;
        Pixel p2 = new Pixel(b2);
        ps.add(p2);
        r = new Rouleau(ps);
        assertEquals("Taille min must be", 14, r.compresseur());
    }

    @Test
    public void testSmallPicCompress(){
        List<Pixel>ps = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            byte b = 0; //127 -> 01111111
            Pixel p = new Pixel(b);
            ps.add(p);
        }
        for (int i = 0; i < 10; i++) {
            byte b = -96; //31 -> 00011111
            Pixel p = new Pixel(b);
            ps.add(p);
        }
        r = new Rouleau(ps);
        assertEquals("Taille min must be", 142, r.compresseur());
    }
}