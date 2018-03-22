package no.cx.iot.philipshueapi.hueAPI;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import no.cx.iot.philipshueapi.hueAPI.bridge.Bridge;
import no.cx.iot.philipshueapi.hueAPI.bridge.DummyBridge;
import no.cx.iot.philipshueapi.hueAPI.bridge.SDKBridge;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKFacade;

@ApplicationScoped
class BridgeSelector {

    @Inject
    @ConfigProperty(name = "useRealBridge", defaultValue = "false")
    private boolean useRealBridge;

    @Inject
    private SDKFacade sdk;

    @Inject
    private Logger logger;

    void waitUntilBridgeIsSelected() {
        int counter = 0;
        if (!useRealBridge) {
            return;
        }
        while (sdk.getSelectedBridge() == null) {
            try {
                logger.info("Waiting for bridge selection");
                Thread.sleep(400);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter++ > 50) {
                throw new HueAPIException("Waited too long for bridgeselection");
            }
        }
    }

    Bridge getBridge() {
        return useRealBridge ? new SDKBridge(sdk.getSelectedBridge()) : new DummyBridge();
    }
}
