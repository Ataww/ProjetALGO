package reader;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class TestRawReader {
	
	private RawReader reader;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetData() throws IOException {
		reader = new RawReader("Baboon.raw");
		assert(reader.getData().length != 0);
	}

}
