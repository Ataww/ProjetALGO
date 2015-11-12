/**
 * 
 */
package util;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import compresseur.Pixel;

/**
 * @author Ataww
 *
 */
public class TestSegReader {
	
	private SegReader reader;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		reader = new SegReader("Lena_g.seg");
	}

	@Test
	public void test() throws IOException {
		List<Pixel> px = reader.read();
		RawWriter writer = new RawWriter("testBig.raw");
		writer.write(px);
	}

}
