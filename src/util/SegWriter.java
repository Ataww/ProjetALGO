package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
		int begin = 0;
		while(begin < inputData.length) {
			Sequence s = sequences[begin];
			output.writeUnsignedInt(8, s.nbPixels);
			output.writeUnsignedInt(3, s.compression);
			for(int j = begin;j < begin+ s.nbPixels && j < inputData.length; j++) {
				output.writeUnsignedInt(8-s.compression, inputData[j]);
			}
			begin += s.nbPixels;
		}
		output.align(1);
	}
	
	public void setInputData(byte[] data) {
		this.inputData = data;
	}

}
