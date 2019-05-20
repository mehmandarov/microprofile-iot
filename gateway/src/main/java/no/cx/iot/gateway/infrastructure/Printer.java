package no.cx.iot.gateway.infrastructure;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Printer {

    public void println(Object object) {
        System.out.println(object);
    }
}
