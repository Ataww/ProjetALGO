package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.jinahya.bit.io.BitOutput;
import com.github.jinahya.bit.io.DelegatedBitOutput;
import com.github.jinahya.bit.io.StreamOutput;

import compresseur.Sequence;

/**
 * <p>
 * 
 * @author Ataww
 *
 */
public class SegWriter {
	
	private BitOutput output;
	private byte[] inputData;
	
	
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
	 * 
	 * @param outputName
	 * @param data
	 * @throws FileNotFoundException
	 */
	public SegWriter(String outputName, byte[] data) throws FileNotFoundException {
		this(outputName);
		inputData = data;
	}
	
	/**
	 * <p>
	 * 
	 * @param sequences
	 * @throws IOException
	 */
	public void write(Sequence[] sequences) throws IOException {
		int i = 0;
		for(Sequence s : sequences) {
			output.writeInt(8, s.nbPixels);
			output.writeInt(3, s.compression);
			for(int j = i;j < i+ s.nbPixels && j < inputData.length; j++) {
				output.writeUnsignedInt(8-s.compression, inputData[j]);
			}
			i += s.nbPixels;
		}
		output.align(1);
	}
	
	public void setInputData(byte[] data) {
		this.inputData = data;
	}

}
