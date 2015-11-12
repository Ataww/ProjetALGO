package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.jinahya.bit.io.BitOutput;
import com.github.jinahya.bit.io.DelegatedBitOutput;
import com.github.jinahya.bit.io.StreamOutput;

import compresseur.Pixel;
import compresseur.Sequence;

/**
 * <p>
 * 
 * @author Ataww
 *
 */
public class SegWriter {
	
	private BitOutput output;
	
	
	/**
	 * <p>
	 * 
	 * @param outputName
	 * @throws FileNotFoundException
	 */
	public SegWriter(String outputName) throws FileNotFoundException {
		output = new DelegatedBitOutput(new StreamOutput(new FileOutputStream(outputName)));
		
	}
	
	/**
	 * <p>
	 * 
	 * @param sequences
	 * @throws IOException
	 */
	public void write(List<Sequence> sequences) throws IOException {
		for(Sequence s : sequences) {
			output.writeInt(8, s.getNbPixel());
			output.writeInt(3, s.getCompressionSize());
			for(Pixel p : s.getPixels()) {
				output.writeInt(s.getCompressionSize(), p.getValue());
			}
		}
		output.align(1);
	}

}
