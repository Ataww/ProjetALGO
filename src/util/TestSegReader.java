/**
 * 
 */
package util;

import org.junit.Before;
import org.junit.Test;

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
		reader = new SegReader("write.test");
	}

	@Test
	public void test() {
		reader.read();
	}

}
