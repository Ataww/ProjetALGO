package util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import reader.RawReader;

public class TestImgMultiplier {
	
	private ImgMultiplier multiplier;
	private int length;

	@Before
	public void setUp() throws Exception {
		RawReader reader = new RawReader("Barbara.raw");
		reader.read(false);
		byte[] data = reader.getData();
		length = data.length;
		multiplier = new ImgMultiplier(data);
	}

	@Test
	public void testGrow() throws IOException {
		//byte[] input = {0b1, 0b0, 0b1, 0b0, 0b1, 0b0, 0b1, 0b0, 0b1};
		//multiplier = new ImgMultiplier(input);
		byte[] res = multiplier.grow();
		assertEquals(length*4, res.length);
		assertEquals(res[1], res[0]);
		RawWriter rw = new RawWriter("Barbara_g.raw");
		rw.write(res);
	}

}
