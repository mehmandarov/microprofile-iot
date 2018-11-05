package no.cx.iot.gateway.infrastructure;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoggerProviderTest {

    @Test
    public void providesLoggerForExpectedClass() {
        assertThat(LoggerProvider.getLogger(Integer.class).getName(), is("Integer"));
    }

}