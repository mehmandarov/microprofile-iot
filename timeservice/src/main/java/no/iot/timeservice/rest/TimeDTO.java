package no.iot.timeservice.rest;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeDTO {
    private final String timeRepresentation;

    public TimeDTO(LocalDateTime localDateTime) {
        timeRepresentation = localDateTime.toString();
    }
}
