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
        return new WeatherDTO(weatherInputProvider.getTemperature());
    }
}