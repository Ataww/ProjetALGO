/**
 * 
 */
package reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.github.jinahya.bit.io.ByteOutput;
import com.github.jinahya.bit.io.DelegatedBitInput;
import com.github.jinahya.bit.io.DelegatedBitOutput;
import com.github.jinahya.bit.io.StreamInput;
import com.github.jinahya.bit.io.StreamOutput;

/**
 * @author Ataww
 *
 */
public class RawReader {

	private byte[] data;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ByteOutput bo = new StreamOutput(new FileOutputStream("test.test"));
		BitOutput bio = new DelegatedBitOutput(bo);
		for(int i = 0; i < 5; i++) {
			bio.writeBoolean(i % 2 == 0);
		}
		BitInput bii = new DelegatedBitInput(new StreamInput(new FileInputStream("test.test")));
		for(int i = 0; i < 5; i++) {
			System.out.println(bii.readBoolean());
		}
	}
	
	

	public RawReader(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		long length = new File(path).length();
		int m = (int) Math.sqrt(length);
		data = new byte[m * m];
		byte[] buffer = new byte[m];
		int offset = 0;
		boolean flip = false;
		while ((fis.read(buffer)) != -1) {
			if (flip) {
				for (int i = m - 1, j = offset; i >= 0; i--, j++) {
					data[j] = buffer[i];
				}
			} else {
				for (int i = 0; i < m; i++) {
					data[i + offset] = buffer[i];
				}
			}
			offset += m;
			flip = !flip;
		}
		fis.close();
	}

}
