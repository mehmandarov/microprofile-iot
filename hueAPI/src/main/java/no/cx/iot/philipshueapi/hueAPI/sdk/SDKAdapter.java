package no.cx.iot.philipshueapi.hueAPI.sdk;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHNotificationManager;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeConfiguration;
import com.philips.lighting.model.PHBridgeResourcesCache;

import no.cx.iot.philipshueapi.hueAPI.bridge.Bridge;
import no.cx.iot.philipshueapi.hueAPI.bridge.DummyBridge;
import no.cx.iot.philipshueapi.hueAPI.bridge.SDKBridge;
import no.cx.iot.philipshueapi.hueAPI.logic.Listener;

@ApplicationScoped
public class SDKAdapter {

    @Inject
    @ConfigProperty(name = "useRealBridge", defaultValue = "false")
    private boolean useRealBridge;

    private PHHueSDK sdk;

    public SDKAdapter() {
        if (useRealBridge) sdk = PHHueSDK.getInstance();
    }

    public void connect(PHAccessPoint accessPoint) {
        if (useRealBridge) sdk.connect(accessPoint);
    }

    private Optional<PHBridge> getSelectedBridge() {
        return useRealBridge ? Optional.of(sdk.getSelectedBridge()) : Optional.empty();
    }

    PHNotificationManager getNotificationManager() {
        return useRealBridge ? sdk.getNotificationManager() : null;
    }

    public void setSelectedBridge(PHBridge bridge) {
        if (useRealBridge) {
            sdk.setSelectedBridge(bridge);
        }
    }

    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        if (useRealBridge) sdk.startPushlinkAuthentication(accessPoint);
    }

    public Bridge getBridge() {
        return useRealBridge ? new SDKBridge(sdk.getSelectedBridge()) : new DummyBridge();
    }

    public void unregisterSDKListener(Listener listener) {
        if (useRealBridge) getNotificationManager().unregisterSDKListener(listener);
    }

    public void registerSDKListener(Listener listener) {
        if (useRealBridge) getNotificationManager().registerSDKListener(listener);
    }

    public boolean isBridgeSelected() {
        return useRealBridge ? (sdk.getSelectedBridge() == null) : true;
    }

    public void search(boolean b, boolean b1) {
        if (useRealBridge) ((PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE)).search(b, b1);
    }

    public void enableHeartbeatHBInterval() {
        getSelectedBridge().ifPresent(bridge -> sdk.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL));
    }

    public Optional<String> getConnectedIPAddress() {
        return getSelectedBridge()
                .map(PHBridge::getResourceCache)
                .map(PHBridgeResourcesCache::getBridgeConfiguration)
                .map(PHBridgeConfiguration::getIpAddress);
    }

}