package no.cx.iot.philipshueapi.hueController.rest.infrastructure;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@SuppressWarnings("unused")
public class LoggerProvider {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        Class<?> clazz = injectionPoint.getMember().getDeclaringClass();
        return getLogger(clazz);
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getSimpleName());
    }
}
