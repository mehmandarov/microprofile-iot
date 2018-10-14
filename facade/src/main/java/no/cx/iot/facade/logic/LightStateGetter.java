package no.cx.iot.facade.logic;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import no.cx.iot.facade.bridge.Bridge;
import no.cx.iot.facade.lightstate.Brightness;
import no.cx.iot.facade.lightstate.LightState;

import static java.util.Optional.ofNullable;

@RequestScoped
class LightStateGetter {

    @Inject
    private Logger logger;

    PHLight getGivenLight(Bridge bridge, int lightIndex) {
        return getAllLights(bridge).get(lightIndex);
    }

    List<PHLight> getAllLights(Bridge selectedBridge) {
        return selectedBridge.getAllLights();
    }

    LightState getLightState(Bridge bridge, int lightIndex, PHLightState lightState) {
        return new LightState(lightIndex, bridge.getInputSource(), getBrightness(lightState), lightState.getHue());
    }

    private Brightness getBrightness(PHLightState lightState) {
        return ofNullable(lightState.getBrightness())
                .map(Brightness::new)
                .orElseGet(Brightness::getMaxBrightness);
    }

    PHLightState getLastKnownLightState(Bridge bridge, int lightIndex, PHLight light) {
        return ofNullable(bridge.getLastKnownLightState(light))
                .filter(PHLightState::isReachable)
                .orElseThrow(() -> new RuntimeException("Light " + lightIndex + " is not reachable."));
    }
}
