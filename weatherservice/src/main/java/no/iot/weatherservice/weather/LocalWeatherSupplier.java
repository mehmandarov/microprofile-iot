package no.iot.weatherservice.weather;

import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LocalWeatherSupplier implements Supplier<WeatherDTO> {

    @Inject
    private WeatherInputProvider weatherInputProvider;

    @Override
    public WeatherDTO get() {
        return new WeatherDTO(weatherInputProvider.getTemperature());
    }
}