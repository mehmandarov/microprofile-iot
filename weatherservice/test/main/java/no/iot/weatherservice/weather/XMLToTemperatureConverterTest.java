package no.iot.weatherservice.weather;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.io.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class XMLToTemperatureConverterTest {

    @InjectMocks
    private XMLToTemperatureConverter converter;

    @Mock
    private Logger logger;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parsesTemperatureFromExampleYrFileCorrectly() throws IOException {
        String entireXML = readFile("exampleOfYrResponse.txt").stream().collect(Collectors.joining("\n"));

        String temperature = converter.convert(entireXML);

        assertThat(temperature, is("-2.7"));
    }

    @Test
    public void parsesTemperatureFromChisinauResultsCorrectly() throws IOException {
        String entireXML = readFile("chisinau.xml").stream().collect(Collectors.joining("\n"));

        String temperature = converter.convert(entireXML);

        assertEquals(temperature, "2");

    }

    @Test
    public void returnsErrorIfNoProperWeatherForecastProvided() {
        String returnedMessage = converter.convert("ERROR trall");
        assertThat(returnedMessage, is("ERROR"));
    }

    @Test
    public void returnsErrorIfInputIsJustRubbish() {
        String returnedMessage = converter.convert("trall");
        assertThat(returnedMessage, is("ERROR"));
    }

    private List<String> readFile(String filename) throws IOException {
        File file = Paths.get(".").resolve("src/main").resolve("resources").resolve(filename).toAbsolutePath().toFile();
        return Files.readLines(file, Charset.defaultCharset());
    }

}