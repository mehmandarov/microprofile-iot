package no.cx.iot.philipshueapi.hueAPI.logic;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.model.PHBridge;

import no.cx.iot.philipshueapi.hueAPI.HueProperties;
import no.cx.iot.philipshueapi.hueAPI.sdk.SDKAdapter;

@ApplicationScoped
class BridgeConnector {

    @SuppressWarnings("unused")
    @Inject
    private SDKAdapter sdk;

    @SuppressWarnings("unused")
    @Inject
    private HueProperties hueProperties;

    void connectToLastKnownAccessPoint() {
        Optional<String> username = Optional.ofNullable(hueProperties.getUsername());
        Optional<String> lastIpAddress = Optional.ofNullable(hueProperties.getLastConnectedIP());

        if (!username.isPresent() || !lastIpAddress.isPresent()) {
            return;
        }

        createAndConnectToAccessPoint(username.get(), lastIpAddress.get());
    }

    private void createAndConnectToAccessPoint(String username, String lastIpAddress) {
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        connect(accessPoint);
    }

    void findBridges() {
        sdk.search(true, true);
    }

    private void connect(PHAccessPoint accessPoint) {
        sdk.getConnectedIPAddress().ifPresent(ip -> sdk.connect(accessPoint));
    }

    void onBridgeConnected(PHBridge bridge, String username) {
        sdk.setSelectedBridge(bridge);
        sdk.enableHeartbeatHBInterval();
        hueProperties.storeConnectionData(username, getLastIpAddress(bridge));
    }

    private String getLastIpAddress(PHBridge bridge) {
        return bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
    }

    void connectToArbitraryAccessPoint(List<PHAccessPoint> list) {
        list.stream()
                .limit(1)
                .forEach(this::connect);
    }
}
