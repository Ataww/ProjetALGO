package util;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestRawWriter {
	
	private RawWriter writer;

	@Before
	public void setUp() throws Exception {
		writer = new RawWriter("rawWrite.test");
		
	}

	@Test
	public void testWrite() throws IOException {
		byte[] input = new byte[] { 127, -128, 12, 24, 26, 17, -48, 16, 58, 58, - 112 };
		writer.write(input);
	}

}
