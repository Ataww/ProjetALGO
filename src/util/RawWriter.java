/**
 * 
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author ncouret
 *
 */
public class RawWriter {

	private String path;

	/**
	 * 
	 */
	public RawWriter(String path) {
		this.path = path;
	}

	public void write(byte[] input) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(input);
			fos.flush();
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

}
