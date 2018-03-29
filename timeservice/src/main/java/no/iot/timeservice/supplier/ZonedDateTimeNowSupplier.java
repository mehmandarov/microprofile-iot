package no.iot.timeservice.supplier;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.iot.timeservice.TimeDTO;

@ApplicationScoped
public class ZonedDateTimeNowSupplier implements Supplier<TimeDTO> {

    @Inject
    private LocalDateTimeNowSupplier localDateTimeNowSupplier;

    @Inject
    @ConfigProperty(name = "user.timezone")
    private String user_timezone;

    @Override
    public TimeDTO get() {
        LocalDateTime now = localDateTimeNowSupplier.get();
        ZoneId zoneId = getZoneId();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(now, zoneId);
        return new TimeDTO(zonedDateTime);
    }

    ZoneId getZoneId() {
        return ZoneId.of(user_timezone);
    }
}
