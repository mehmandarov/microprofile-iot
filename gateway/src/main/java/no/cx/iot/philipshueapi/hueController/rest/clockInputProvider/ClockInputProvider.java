package no.cx.iot.philipshueapi.hueController.rest.clockInputProvider;

import no.cx.iot.philipshueapi.hueController.rest.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.LightState;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class ClockInputProvider implements InputProvider {

    @Inject
    @SuppressWarnings("unused")
    private LocalDateTimeNowSupplier localDateTimeSupplier;

    @Override
    public LightState getNewStateForLight(int lightIndex) {
        LocalDateTime now = localDateTimeSupplier.get();

        int newBrightness = now.getNano() % 255;
        return new LightState(new Brightness(newBrightness), null);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
