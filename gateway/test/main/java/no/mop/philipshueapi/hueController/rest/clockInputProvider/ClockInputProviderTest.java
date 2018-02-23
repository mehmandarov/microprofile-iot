package no.cx.iot.philipshueapi.hueController.rest.clockInputProvider;

import no.cx.iot.philipshueapi.hueController.rest.LightState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ClockInputProviderTest {

    @Spy
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    @InjectMocks
    private ClockInputProvider clockInputProvider;

    @Test
    public void brightness0IfNano0() {
        setLocalDateTime(0);
        LightState newStateForLight = clockInputProvider.getNewStateForLight(0);
        assertThat(newStateForLight.getBrightness(), is(0));
    }

    @Test
    public void brightness100IfNano100() {
        setLocalDateTime(100);
        LightState newStateForLight = clockInputProvider.getNewStateForLight(0);
        assertThat(newStateForLight.getBrightness(), is(100));
    }

    @Test
    public void brightness254IfNano254() {
        setLocalDateTime(254);
        LightState newStateForLight = clockInputProvider.getNewStateForLight(0);
        assertThat(newStateForLight.getBrightness(), is(254));
    }

    @Test
    public void brightness0IfNano255() {
        setLocalDateTime(255);
        LightState newStateForLight = clockInputProvider.getNewStateForLight(0);
        assertThat(newStateForLight.getBrightness(), is(0));
    }

    private void setLocalDateTime(int nanoOfSecond) {
        LocalDate localDate = LocalDate.of(2017, Month.JANUARY, 1);
        LocalTime localTime = LocalTime.of(16, 20, 0, nanoOfSecond);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Mockito.doReturn(localDateTime).when(localDateTimeNowSupplier).get();
    }

}