package no.cx.iot.gateway.infrastructure;

import java.io.IOException;

import org.eclipse.microprofile.metrics.annotation.Timed;

public interface Connector {

    @Timed(absolute = true, name = "canConnect", description = "Can connect-checks")
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
