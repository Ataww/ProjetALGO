package reader;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRawReader {
	
	private RawReader reader;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetData() throws IOException {
		reader = new RawReader("Baboon.raw");
		assertTrue(reader.getData().length != 0);
		long length = new File("Baboon.raw").length();
		assertEquals(length, reader.getData().length);
	}

}
