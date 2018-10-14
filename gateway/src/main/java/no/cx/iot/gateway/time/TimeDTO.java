package no.cx.iot.gateway.time;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeDTO {
    private String timeRepresentation;

    public ZonedDateTime getDateTime() {
        return ZonedDateTime.parse(timeRepresentation);
    }
}