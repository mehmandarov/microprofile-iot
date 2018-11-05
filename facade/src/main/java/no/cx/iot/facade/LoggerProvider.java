package no.cx.iot.facade;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@SuppressWarnings("unused")
public class LoggerProvider {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getSimpleName());
    }
}
