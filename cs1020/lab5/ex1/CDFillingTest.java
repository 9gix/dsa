package lab5.ex1;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class CDFillingTest {

    private final ByteArrayOutputStream out_stream = new ByteArrayOutputStream();
    private String expected = null;

    @Parameters
    public static Collection<Object[]> getTestParameters() {
        return Arrays.asList(new Object[][] {
                { "cd1.in", "cd1.out" },
                { "cd2.in", "cd2.out" },
                { "cd3.in", "cd3.out" },
                { "cd4.in", "cd4.out" },
                { "cd5.in", "cd5.out" }, });
    }

    /**
     * Constructor Methods, the parameter will be automatically filled by the @parameters
     * method, Note: see parameterized junit for more information
     * 
     * @param input_file
     * @param output_file
     */
    public CDFillingTest(String input_file, String output_file) {

        // Input
        InputStream in_stream = this.getClass().getResourceAsStream(input_file);

        // Any Prompt of Input will receive the in_stream from the input file.
        System.setIn(in_stream);

        // Output
        URL url = this.getClass().getResource(output_file);
        try {
            Path resource_path = Paths.get(url.toURI());
            expected = new String(Files.readAllBytes(resource_path));
        } catch (URISyntaxException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(out_stream));
    }

    @After
    public void tearDownStreams() {
        System.setOut(null);
    }

    @Test
    public void testMainApp() {

        // Call the Program
        CDFilling.main(null);

        // Assert the program output match with the testdata output.
        assertEquals(expected, out_stream.toString());

    }

}