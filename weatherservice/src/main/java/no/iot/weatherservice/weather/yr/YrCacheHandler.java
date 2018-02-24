package no.iot.weatherservice.weather.yr;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.iot.weatherservice.weather.Tuple;
import no.iot.weatherservice.weather.WeatherCacheEntry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static no.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;


@ApplicationScoped
public class YrCacheHandler {

    private Path path;

    private Map<String, Tuple<LocalDateTime, String>> cache = new HashMap<>();

    @PostConstruct
    public void createCacheIfNotExisting() {
        path = Paths.get("yr_cache.json");
        if (!Files.exists(path)) {
            wrapExceptions(() -> Files.createFile(path));
        }
        Optional.ofNullable(wrapExceptions(this::readCache)).ifPresent(entry ->
                cache.put(entry.getPlace(), new Tuple<>(LocalDateTime.parse(entry.getTime()), entry.getTemperature())));
    }

    private WeatherCacheEntry readCache() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (Files.size(path) == 0) {
            return null;
        }

        return objectMapper.readValue(path.toFile(), WeatherCacheEntry.class);
    }

    private boolean save(String currentLocation, LocalDateTime now, String temperature) throws IOException {
        WeatherCacheEntry cacheEntries = new WeatherCacheEntry(currentLocation, now.toString(), temperature);
        ObjectMapper mapper = new ObjectMapper();
        if (path == null) {
            createCacheIfNotExisting();
        }

        Files.write(path, Collections.singletonList(mapper.writeValueAsString(cacheEntries)), StandardOpenOption.APPEND);
        return true;
    }

    Optional<String> getFromNewlyUpdatedCache(String currentLocation) {
        if (cache.containsKey(currentLocation)) {
            Tuple<LocalDateTime, String> tuple = cache.get(currentLocation);
            if (isNewlyUpdatedSoUseCache(tuple.getFirst())) {
                return Optional.of(tuple.getSecond());
            }
        }
        return Optional.empty();
    }

    private boolean isNewlyUpdatedSoUseCache(LocalDateTime cacheEntryUpdateTime) {
        return cacheEntryUpdateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(1)));
    }

    void updateCache(String currentLocation, String temperature) {
        wrapExceptions(() -> save(currentLocation, LocalDateTime.now(), temperature));
        cache.put(currentLocation, new Tuple<>(LocalDateTime.now(), temperature));
    }

}
