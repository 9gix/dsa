import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

public class OperationTestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
    public void testAddition() {
        assertEquals("15", SpaceshipMomentum.add("5", "10", 10));
        assertEquals("AF", SpaceshipMomentum.add("16", "99", 16));
    }
    	
	@Test
	public void testSubstraction() {
//		assertEquals("1", SpaceshipMomentum.sub("100", "99", 10));
//		assertEquals("50", SpaceshipMomentum.sub("FF", "AF", 16));
//		assertEquals("11", SpaceshipMomentum.sub("101", "10", 2));
//		assertEquals("4547511", SpaceshipMomentum.sub("4788523", "241012", 10));
		assertEquals("53868920", SpaceshipMomentum.sub("54795300", "926380", 10));
	}
	
	@Test
	public void testComplement(){
	    assertEquals("55", SpaceshipMomentum.nthComplement("45", 10, 2));
	    assertEquals("9955", SpaceshipMomentum.nthComplement("45", 10, 4));
	}
}
