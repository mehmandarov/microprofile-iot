package no.cx.iot.gateway.infrastructure;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@SuppressWarnings("unused")
public class LoggerProvider {

    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    public static Logger getLogger(Class<?> clazz) {
        return Logger.getLogger(clazz.getSimpleName());
    }
}
