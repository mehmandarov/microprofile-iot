package no.iot.timeservice.supplier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ZonedDateTimeNowSupplierTest {

    @Mock
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    @InjectMocks
    @Spy
    private ZonedDateTimeNowSupplier zonedDateTimeNowSupplier;

    @Test
    public void includesTimeZoneInResult() {
        doReturn(ZoneId.of("Europe/Moscow")).when(zonedDateTimeNowSupplier).getZoneId();
        LocalDateTime now = LocalDateTime.of(LocalDate.of(2010, 1, 1), LocalTime.of(12, 30, 0));
        doReturn(now).when(localDateTimeNowSupplier).get();

        ZonedDateTime parsed = ZonedDateTime.parse(zonedDateTimeNowSupplier.get().getTimeRepresentation());

        assertThat(parsed, is("2010-01-01T12:30+03:00[Europe/Moscow]"));
    }
}