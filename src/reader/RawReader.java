/**
 * 
 */
package reader;

import compresseur.Pixel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * @author couretn
 *
 */
public class RawReader {

	private byte[] data;
	private long length;
	private String path;

	public RawReader(String path) throws IOException {
		this.path = path;
		length = new File(path).length();
		data = new byte[(int) length];

	}

	public void read(boolean dauphin) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			int m = (int) Math.sqrt(length);
			byte[] buffer = new byte[m];
			int offset = 0;
			if (dauphin) {
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
			} else {
				while ((fis.read(buffer)) != -1) {
					for (int i = 0; i < m; i++) {
						data[i + offset] = buffer[i];
					}
				}
				offset += m;
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	public byte[] getData() {
		return data;
	}

    public List<Pixel> buildList() {
        List<Pixel> pixels = new ArrayList<>(data.length);
        for (byte b : data) {
            pixels.add(new Pixel(b));
        }
        return pixels;
    }

}
