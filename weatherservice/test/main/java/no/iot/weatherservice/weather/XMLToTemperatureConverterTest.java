package no.iot.weatherservice.weather;


import com.google.common.io.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class XMLToTemperatureConverterTest {

    @InjectMocks
    private XMLToTemperatureConverter converter;

    @Test
    public void parsesTemperatureFromExampleYrFileCorrectly() throws IOException {
        String entireXML = readFile().stream().collect(Collectors.joining("\n"));

        String temperature = converter.convert(entireXML);

        assertThat(temperature, is("-2.7"));
    }

    private List<String> readFile() throws IOException {
        File file = Paths.get(".").resolve("src/main").resolve("resources").resolve("exampleOfYrResponse.txt").toAbsolutePath().toFile();
        return Files.readLines(file, Charset.defaultCharset());
    }

}