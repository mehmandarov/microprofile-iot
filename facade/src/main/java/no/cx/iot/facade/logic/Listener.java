package no.cx.iot.facade.logic;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;

import no.cx.iot.facade.HueProperties;

@ApplicationScoped
public class Listener implements PHSDKListener {

    @Inject
    private HueProperties hueProperties;

    @Inject
    private BridgeConnector bridgeConnector;

    @Inject
    private Logger logger;

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
    }

    @Override
    public void onBridgeConnected(PHBridge bridge, String username) {
        bridgeConnector.onBridgeConnected(bridge, username);
    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint accessPoint) {
        bridgeConnector.startPushlinkAuthentication(accessPoint);
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
