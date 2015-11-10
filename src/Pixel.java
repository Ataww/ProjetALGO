/**
 * Created by Thomas on 10/11/15.
 */
public class Pixel {
    byte shadeOfGrey;

    public Pixel(byte shadeOfGrey) {
        this.shadeOfGrey = shadeOfGrey;
    }

    public int getMaxCompression(){
        int t = shadeOfGrey+127;
        if (t > 127)
            return 0;
        else if (t > 63)
            return 1;
        else if (t > 31){
            return 2;
        } else if (t > 15){
            return 3;
        } else if (t > 7){
            return 4;
        } else if (t > 3){
            return 5;
        } else if (t > 1){
            return 6;
        } else {
            return 7;
        }
    }
}
