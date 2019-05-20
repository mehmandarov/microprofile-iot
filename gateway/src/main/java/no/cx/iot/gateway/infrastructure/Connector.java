package no.cx.iot.gateway.infrastructure;

import java.io.IOException;

public interface Connector {

    default boolean canConnect() {
        try {
            testConnection();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    void testConnection() throws IOException;
}
