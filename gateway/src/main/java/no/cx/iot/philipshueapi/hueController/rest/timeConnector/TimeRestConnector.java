package no.cx.iot.philipshueapi.hueController.rest.timeConnector;

import no.cx.iot.philipshueapi.hueController.rest.InputProvider;
import no.cx.iot.philipshueapi.hueController.rest.hueAPI.HttpConnector;
import no.cx.iot.philipshueapi.hueController.rest.lights.Brightness;
import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class TimeRestConnector implements InputProvider<LocalDateTime> {

    @Inject
    @ConfigProperty(name = "timeHost", defaultValue = "localhost")
    private String host;

    @Inject
    @ConfigProperty(name = "timePort", defaultValue = "8082")
    private String port;

    @Inject
    @ConfigProperty(name = "timePath", defaultValue = "timeservice")
    private String path;

    private String getFullURL() {
        return "http://" + host + ":" + port +"/" + path;
    }

    @Inject
    private HttpConnector connector;

    public String canConnect() {
        try {
            return "OK, the current time is " + getTime();
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getTime() throws IOException {
        return connector.executeHTTPGet(getFullURL());
    }

    //TODO: The hard-wiring to LocalDateTime here is somewhat shady. Should be encoded somewhere
    @Override
    public LocalDateTime getDataForLight(int lightIndex) {
        return LocalDateTime.parse(wrapExceptions(this::getTime));
    }

    @Override
    public LightState getNewStateForLight(int lightIndex) {
        LocalDateTime dataForLight = getDataForLight(lightIndex);
        int newBrightness = dataForLight.getNano() % 255; // TODO: Yes, a bit nonsensical and magical
        return new LightState(new Brightness(newBrightness), null);
    }
}
