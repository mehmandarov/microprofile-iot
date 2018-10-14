package no.cx.iot.philipshueapi.hueAPI.sdk;

import com.philips.lighting.hue.sdk.PHBridgeSearchManager;

public class EmptyPHBridgeSearchManager implements PHBridgeSearchManager {
    @Override
    public void search(boolean b, boolean b1) {

    }

    @Override
    public void search(boolean b, boolean b1, boolean b2) {

    }

    @Override
    public void upnpSearch() {

    }

    @Override
    public void portalSearch() {

    }

    @Override
    public void ipAddressSearch() {

    }

    @Override
    public void setPortalAddress(String s) {

    }

    @Override
    public String getPortalAddress() {
        return null;
    }
}
