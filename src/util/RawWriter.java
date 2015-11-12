/**
 * 
 */
package util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import compresseur.Pixel;

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
	
	public void write(List<Pixel> pixels) throws IOException {
		byte[] pxl = new byte[pixels.size()];
		for(int i = 0; i < pixels.size(); i++) {
			pxl[i] = pixels.get(i).getValue();
		}
		write(pxl);
	}

}
