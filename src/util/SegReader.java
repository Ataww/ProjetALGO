/**
 * 
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.DelegatedBitInput;
import com.github.jinahya.bit.io.StreamInput;
import compresseur.Pixel;

/**
 * @author Ataww
 *
 */
public class SegReader {
	
	private BitInput input;

	public SegReader(String path) throws FileNotFoundException {
		input = new DelegatedBitInput(new StreamInput(new FileInputStream(path)));
	}
	
	public List<Pixel> read() {
		List<Pixel> data = new ArrayList<Pixel>();
		boolean running = true;
		while(running) {
			try {
				int length = input.readUnsignedInt(8);
				int cpr = input.readUnsignedInt(3);
				for(int i = 0; i < length;i++) {
					byte value = (byte) input.readUnsignedInt(8-cpr);
					data.add(new Pixel(value));
				}
			} catch(IOException e) {
				running = false;
			}
		}
		return data;
	}
}
