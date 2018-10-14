package no.cx.iot.philipshueapi.hueAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class HueProperties {

    private final String LAST_CONNECTED_IP   = "LastIPAddress";
    private final String USER_NAME           = "WhiteListUsername";
    private final String SECRETS_FILE_NAME   = "/run/secrets/huecredentials";
    private Properties properties;
    
    private void storeLastIPAddress(String ipAddress) {
        properties.setProperty(LAST_CONNECTED_IP, ipAddress);
    }

    @Inject
    private Logger logger;

    @PostConstruct
    public void setUp() {
        loadProperties();
    }

    private void storeUsername(String username) {
        properties.setProperty(USER_NAME, username);
    }

    public String getUsername() {
        return properties.getProperty(USER_NAME);
    }

    public String getLastConnectedIP() {
        return properties.getProperty(LAST_CONNECTED_IP);
    }

    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();

            if (propertyFileMissing()) {
                return;
            }

            loadPropertiesFromFile();
        }
    }

    private boolean propertyFileMissing() {
        return !Files.exists(Paths.get(SECRETS_FILE_NAME));
    }

    private void loadPropertiesFromFile() {
        try(FileInputStream in = new FileInputStream(SECRETS_FILE_NAME)) {
            properties.load(in);
        }
        catch (FileNotFoundException fe) {
            logger.warning("File doesn't exist. Not necessarily a problem. Doing nothing");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void storeConnectionData(String username, String lastIpAddress) {
        if (propertyFileMissing()) {
            storeUsername(username);
            storeLastIPAddress(lastIpAddress);
        }
    }
}