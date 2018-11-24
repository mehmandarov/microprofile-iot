package no.cx.iot.gateway.time;

import java.time.ZonedDateTime;

public class TimeDTO {
    private String timeRepresentation;

    public ZonedDateTime getDateTime() {
        return ZonedDateTime.parse(timeRepresentation);
    }
}
