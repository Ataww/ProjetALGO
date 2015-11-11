package util;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import compresseur.Pixel;
import compresseur.Sequence;

public class TestSegWriter {
	
	private SegWriter writer;

	@Before
	public void setUp() throws Exception {
		writer = new SegWriter("write.test");
	}

	@Test
	public void testWrite() throws IOException {
		Sequence s = new Sequence(5);
		s.add(new Pixel((byte) 0b00010001));
		ArrayList<Sequence> a = new ArrayList<Sequence>();
		a.add(s);
		writer.write(a);
	}
	
	@Test
	public void testMultipleWrite() throws IOException {
		Sequence s = new Sequence(5);
		s.add(new Pixel((byte) 0b00010001));
		s.add(new Pixel((byte) 0b00011011));
		s.add(new Pixel((byte) 0b00010000));
		ArrayList<Sequence> a = new ArrayList<Sequence>();
		a.add(s);
		writer.write(a);
	}

}
