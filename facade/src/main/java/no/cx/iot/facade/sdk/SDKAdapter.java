package no.cx.iot.facade.sdk;

import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeConfiguration;
import com.philips.lighting.model.PHBridgeResourcesCache;

import no.cx.iot.facade.logic.Listener;

@ApplicationScoped
public class SDKAdapter {

    @Inject
    @ConfigProperty(name = "useRealBridge", defaultValue = "false")
    Boolean useRealBridge;

    @Inject
    private Logger logger;

    private SDKFacade sdk;

    @PostConstruct
    public void setup() {
        logger.info("Using real bridge? " + useRealBridge);
        sdk = useRealBridge ? new RealSDKFacade() : new FakeSDKFacade();
    }

    public void connect(PHAccessPoint accessPoint) {
        sdk.connect(accessPoint);
    }

    private Optional<PHBridge> getSelectedBridge() {
        return Optional.ofNullable(sdk.getSelectedBridge());
    }

    public void setSelectedBridge(PHBridge bridge) {
        sdk.setSelectedBridge(bridge);
    }

    public void startPushlinkAuthentication(PHAccessPoint accessPoint) {
        sdk.startPushlinkAuthentication(accessPoint);
    }

    public Bridge getBridge() {
        return useRealBridge ? new SDKBridge(sdk.getSelectedBridge()) : new FakeBridge();
    }

    public void unregisterSDKListener(Listener listener) {
        sdk.getNotificationManager().unregisterSDKListener(listener);
    }

    public void registerSDKListener(Listener listener) {
        sdk.getNotificationManager().registerSDKListener(listener);
    }

    public boolean isBridgeSelected() {
        return useRealBridge ? (sdk.getSelectedBridge() != null) : true;
    }

    public void search(boolean b, boolean b1) {
        ((PHBridgeSearchManager) sdk.getSDKService(PHHueSDK.SEARCH_BRIDGE)).search(b, b1);
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
