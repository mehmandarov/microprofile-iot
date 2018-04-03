package no.cx.iot.philipshueapi.hueController.rest;

import java.io.IOException;

public interface Connector {

    default boolean canConnect() {
        try {
            testConnection();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    boolean testConnection() throws IOException;
}
