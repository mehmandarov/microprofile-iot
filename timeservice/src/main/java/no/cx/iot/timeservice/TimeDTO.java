package no.cx.iot.timeservice;

import java.time.ZonedDateTime;

public class TimeDTO {
    private final String timeRepresentation;

    public TimeDTO(ZonedDateTime zonedDateTime) {
        timeRepresentation = zonedDateTime.toString();
    }
}
