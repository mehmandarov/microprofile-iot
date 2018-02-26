package no.iot.timeservice.supplier;

import no.iot.timeservice.rest.TimeDTO;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@ApplicationScoped
public class LocalDateTimeNowSupplier implements Supplier<TimeDTO> {

    @Override
    public TimeDTO get() {
        return new TimeDTO(LocalDateTime.now());
    }
}
