package no.iot.timeservice.supplier;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalDateTimeNowSupplier implements Supplier<LocalDateTime> {

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
