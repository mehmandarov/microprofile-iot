package no.iot.weatherservice;

import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import no.iot.weatherservice.weather.WeatherInputProvider;

@ApplicationScoped
public class LocalWeatherSupplier implements Supplier<WeatherDTO> {

    @Inject
    private WeatherInputProvider weatherInputProvider;

    @Override
    public WeatherDTO get() {
        WeatherDTO weatherDTO = createWeatherDTO();
        validate(weatherDTO);
        return weatherDTO;
    }

    private WeatherDTO createWeatherDTO() {
        return new WeatherDTO(weatherInputProvider.getTemperature());
    }

    private void validate(WeatherDTO weather) {
        Double.parseDouble(weather.getTemperature());
    }
}