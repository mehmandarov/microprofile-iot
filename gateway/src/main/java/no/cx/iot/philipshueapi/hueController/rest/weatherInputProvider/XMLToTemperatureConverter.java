package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class XMLToTemperatureConverter {

    // Yes, this is obscure. But it works. At least atm. Should be improved.
    String convert(String xml) {
        String[] observations = xml.split("<observations>", 2);
        String[] observation = observations[1].split("<temperature unit=\"celsius\" value=", 2);
        String[] onlyTemperature = observation[1].split("time", 2);
        return onlyTemperature[0].replaceAll("\"", "").trim();
    }

}
