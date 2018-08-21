package no.iot.timeservice.supplier;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.iot.timeservice.TimeDTO;

@ApplicationScoped
public class ZonedDateTimeNowSupplier implements Supplier<TimeDTO> {

    @Inject
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    @Inject
    private ZoneIdSupplier zoneIdSupplier;

    @Override
    public TimeDTO get() {
        LocalDateTime now = localDateTimeNowSupplier.get();
        ZoneId zoneId = zoneIdSupplier.get();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(now, zoneId);
        return new TimeDTO(zonedDateTime);
    }
}
