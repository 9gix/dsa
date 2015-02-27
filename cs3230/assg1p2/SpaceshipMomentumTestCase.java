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


@RunWith(value = Parameterized.class)
public class SpaceshipMomentumTestCase {

	private SpaceshipMomentum app;
	
	InputStream in_stream;
	PrintStream out_stream;
	private String test_data;
	
	ByteArrayOutputStream program_output = new ByteArrayOutputStream();
	
	@Parameters(name = "{index}: SpaceshipMomentum({0})")
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] { {"A"}, {"B"}, {"C"} });
    }
    
    public SpaceshipMomentumTestCase(String test_data){
    	this.test_data = test_data;
    }
	
	@Before
	public void setUp() throws Exception {
		in_stream = System.in;
		out_stream = System.out;
		
		InputStream in_test = this.getClass().getResourceAsStream(this.test_data + ".in");
		PrintStream out_test = new PrintStream(program_output);
		System.setIn(in_test);
		System.setOut(out_test);
		
	}

	@After
	public void tearDown() throws Exception {
		System.setIn(in_stream);
		System.setOut(out_stream);
	}

	@Test
	public void test() throws IOException, URISyntaxException  {
		app = new SpaceshipMomentum();
		String expected = new String(Files.readAllBytes(Paths.get(this.getClass().getResource(this.test_data + ".out").toURI())));
		assertEquals(expected, program_output.toString());
	}
}
