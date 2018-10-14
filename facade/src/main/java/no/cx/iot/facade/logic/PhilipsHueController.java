package no.cx.iot.facade.logic;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.bridge.Bridge;
import no.cx.iot.facade.lightstate.LightState;

@ApplicationScoped
public class PhilipsHueController {

    @Inject
    private SetupController setupController;

    @Inject
    private Logger logger;

    @Inject
    private ColourSetter colorSetter;

    @Inject
    private LightStateGetter lightStateGetter;

    public void setup() {
        setupController.setup();
    }

    public LightState switchStateOfGivenLight(Bridge bridge, int lightIndex, int brightness, int color) {
        PHLight light = lightStateGetter.getGivenLight(bridge, lightIndex);
        PHLightState lastKnownLightState = lightStateGetter.getLastKnownLightState(bridge, lightIndex, light);
        logger.fine("New brightness: " + brightness);
        lastKnownLightState.setBrightness(brightness);
        bridge.updateLightState(light, lastKnownLightState);
        colorSetter.setColorOnLight(bridge, color, light, lastKnownLightState);
        return lightStateGetter.getLightState(bridge, lightIndex, lastKnownLightState);
    }

    public int getNumberOfLights(Bridge bridge) {
        return lightStateGetter.getAllLights(bridge).size();
    }
}
