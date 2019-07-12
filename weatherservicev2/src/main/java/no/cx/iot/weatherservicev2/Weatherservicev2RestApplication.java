package no.cx.iot.weatherservicev2;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@ApplicationPath("/data")
@ApplicationScoped

public class Weatherservicev2RestApplication extends Application {
}
