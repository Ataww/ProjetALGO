/**
 * 
 */
package reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 * @author couretn
 *
 */
public class RawReader {

	private byte[] data;
	
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
	
	public byte[] getData() {
		return data;
	}
 
}
