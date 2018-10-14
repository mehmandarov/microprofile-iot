package no.cx.iot.facade;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.PHAccessPoint;

import no.cx.iot.facade.bridge.Bridge;
import no.cx.iot.facade.sdk.SDKAdapter;

@ApplicationScoped
public class BridgeSelector {

    @Inject
    private SDKAdapter sdk;

    @Inject
    private Logger logger;

    void waitUntilBridgeIsSelected() {
        int counter = 0;
        while (!sdk.isBridgeSelected()) {
            try {
                logger.info("Waiting for bridge selection");
                Thread.sleep(400);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (counter++ > 50) {
                throw new RuntimeException("Waited too long for bridgeselection");
            }
        }
    }

    Bridge getBridge() {
        return sdk.getBridge();
    }

    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }
}
