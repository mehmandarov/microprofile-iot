package no.cx.iot.timeservice;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimeDTO {
    private String timeRepresentation;

    public TimeDTO() {
    }

    public String getTimeRepresentation() {
        return timeRepresentation;
    }

    public void setTimeRepresentation(String timeRepresentation) {
        this.timeRepresentation = timeRepresentation;
    }

    public TimeDTO(ZonedDateTime zonedDateTime) {
        timeRepresentation = zonedDateTime.toString();
    }
}
