package util;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import compresseur.Sequence;

/**
 * 
 * @author ncouret
 *
 */
public class TestSegWriter {
	
	private SegWriter writer;

	@Before
	public void setUp() throws Exception {
		writer = new SegWriter("write.test");
	}

	@Test
	public void testWrite() throws IOException {
		Sequence s = new Sequence();
		s.compression = 5;
		s.nbPixels = 1;
		writer.setInputData(new byte[] {0b00010010});
		ArrayList<Sequence> a = new ArrayList<Sequence>();
		a.add(s);
		writer.write(a);
	}
	
	@Test
	public void testMultipleWrite() throws IOException {
		Sequence s = new Sequence();
		s.compression = 5;
		s.nbPixels = 3;
		writer.setInputData(new byte[] {0b00010001, 0b00011011, 0b00010000});
		ArrayList<Sequence> a = new ArrayList<Sequence>();
		a.add(s);
		writer.write(a);
	}

}
