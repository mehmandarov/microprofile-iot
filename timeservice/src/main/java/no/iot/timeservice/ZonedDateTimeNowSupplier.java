package no.iot.timeservice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ZonedDateTimeNowSupplier implements Supplier<TimeDTO> {

    @Inject
    @ConfigProperty(name = "user.timezone")
    private String user_timezone;

    @Override
    public TimeDTO get() {
        return get(LocalDateTime.now());
    }

    TimeDTO get(LocalDateTime now) {
        ZoneId zoneId = getTimezone();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(now, zoneId);
        return new TimeDTO(zonedDateTime);
    }

    ZoneId getTimezone() {
        return ZoneId.of(user_timezone);
    }
}
