package compresseur;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Thomas on 10/11/15.
 */
public class Sequence {
    private int compressionSize;
    private List<Pixel> pixels;

    public Sequence(int compressionSize) {
        this.compressionSize = compressionSize;
        pixels = new ArrayList<>();
    }

    public int getSize(){
        return 11 + pixels.size()*(8-compressionSize);
    }

    public void add (Pixel pixel){
            pixels.add(pixel);
    }

    public boolean isFull(){
        return pixels.size() >= 256;
    }
    
    public int getNbPixel() {
    	return pixels.size();
    }
    
    public List<Pixel> getPixels() {
    	return pixels;
    }

    public int getCompressionSize() {
        return compressionSize;
    }

    public void setCompressionSize(int compressionSize) {
        this.compressionSize = compressionSize;
    }
}
