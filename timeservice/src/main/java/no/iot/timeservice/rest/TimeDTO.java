package no.iot.timeservice.rest;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class TimeDTO {
    private final String timeRepresentation;

    public TimeDTO(ZonedDateTime zonedDateTime) {
        timeRepresentation = zonedDateTime.toString();
    }
}
