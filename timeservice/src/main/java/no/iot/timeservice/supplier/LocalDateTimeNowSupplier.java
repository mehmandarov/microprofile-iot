package no.iot.timeservice.supplier;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@ApplicationScoped
public class LocalDateTimeNowSupplier implements Supplier<LocalDateTime> {

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
