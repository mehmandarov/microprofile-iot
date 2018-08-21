package no.iot.timeservice.supplier;

import java.time.ZoneId;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
class ZoneIdSupplier implements Supplier<ZoneId> {

    @Inject
    @ConfigProperty(name = "user.timezone")
    private String user_timezone;

    @Override
    public ZoneId get() {
        return ZoneId.of(user_timezone);
    }
}
