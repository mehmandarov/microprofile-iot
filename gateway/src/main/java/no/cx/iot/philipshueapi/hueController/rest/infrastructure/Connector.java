package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

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

    void testConnection() throws IOException;
}
