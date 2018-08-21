package no.cx.iot.philipshueapi.hueAPI.logic;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import no.cx.iot.philipshueapi.hueAPI.BridgeSelector;
import no.cx.iot.philipshueapi.hueAPI.HueProperties;

@ApplicationScoped
public class Listener implements PHSDKListener {

    @SuppressWarnings("unused")
    @Inject
    private HueProperties hueProperties;

    @SuppressWarnings("unused")
    @Inject
    private BridgeConnector bridgeConnector;

    @Inject
    private Logger logger;

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
    }

    @Inject
    private BridgeSelector bridgeSelector;

    @Override
    public void onBridgeConnected(PHBridge bridge, String username) {
        bridgeConnector.onBridgeConnected(bridge, username);
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint accessPoint) {
        logger.warning("Authentication required on " + accessPoint);
        bridgeSelector.startPushlinkAuthentication(accessPoint);
    }

    @Override
    public void onAccessPointsFound(List<PHAccessPoint> list) {
        bridgeConnector.connectToArbitraryAccessPoint(list);
    }

    @Override
    public void onError(int i, String s) {
        logger.severe("Error: " + i + ": " + s);
    }

    @Override
    public void onConnectionResumed(PHBridge phBridge) {
    }

    @Override
    public void onConnectionLost(PHAccessPoint accessPoint) {
        logger.warning("Lost connection to " + accessPoint);
    }

    @Override
    public void onParsingErrors(List<PHHueParsingError> list) {
        logParsingErrors(list);
    }

    private void logParsingErrors(Collection<PHHueParsingError> errors) {
        errors.forEach(str -> logger.severe(str.getMessage()));
    }

}
