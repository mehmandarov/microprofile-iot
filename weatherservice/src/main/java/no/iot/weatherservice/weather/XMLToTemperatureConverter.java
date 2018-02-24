package no.iot.weatherservice.weather;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class XMLToTemperatureConverter {

    // Yes, this is obscure. But it works. At least atm. Should be improved.
    public String convert(String xml) {
        String[] observations = xml.split("<observations>", 2);
        if (observations.length >= 1) {
            if (!observations[0].startsWith("ERROR")) {
                String[] observation = observations[1].split("<temperature unit=\"celsius\" value=", 2);
                String[] onlyTemperature = observation[1].split("time", 2);
                return onlyTemperature[0].replaceAll("\"", "").trim();
            }
        }
        return "ERROR";
    }
}
