package no.cx.iot.philipshueapi.hueAPI;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * HueProperties.java
 * 
 * Stores the last known connected IP Address and the last known username.
 * This facilitates automatic bridge connection. 
 *  
 * Also, as the username (for the whitelist) is a random string,  this prevents the need to 
 * pushlink every time the app is started (as the username is read from the properties file).
 *
 */

@ApplicationScoped
public class HueProperties {

    private final String LAST_CONNECTED_IP   = "LastIPAddress";
    private final String USER_NAME           = "WhiteListUsername";
    private final String PROPS_FILE_NAME     = "MyHue.properties";
    private Properties properties;
    
    private void storeLastIPAddress(String ipAddress) {
        properties.setProperty(LAST_CONNECTED_IP, ipAddress);
        saveProperties();
    }

    @PostConstruct
    public void setUp() {
        loadProperties();
    }
    /**
     * Stores the Username (for Whitelist usage). This is generated as a random 16 character string.
     */
    private void storeUsername(String username) {
        properties.setProperty(USER_NAME, username);
        saveProperties();
    }

    /**
     * Returns the stored Whitelist username.  If it doesn't exist we generate a 16 character random string and store this in the properties file.
     */
    public String getUsername() {
        return properties.getProperty(USER_NAME);
    }

    public String getLastConnectedIP() {
        return properties.getProperty(LAST_CONNECTED_IP);
    }

    // Load in HueProperties, if first time use a properties file is created.
    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();

            if (!Files.exists(Paths.get(PROPS_FILE_NAME))) {
                saveProperties();
                return;
            }

            loadPropertiesFromFile();
        }
    }

    private void loadPropertiesFromFile() {
        try(FileInputStream in = new FileInputStream(PROPS_FILE_NAME)) {
            properties.load(in);
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveProperties() {
        try(FileOutputStream out = new FileOutputStream(PROPS_FILE_NAME)) {
            properties.store(out, null);
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void storeConnectionData(String username, String lastIpAddress) {
        storeUsername(username);
        storeLastIPAddress(lastIpAddress);
        saveProperties();
    }
}