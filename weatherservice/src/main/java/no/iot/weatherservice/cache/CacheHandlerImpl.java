package no.iot.weatherservice.cache;

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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.iot.weatherservice.weather.Temperature;

import static no.iot.weatherservice.utils.general.ExceptionWrapper.wrapExceptions;


@ApplicationScoped
public class CacheHandlerImpl implements CacheHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); // TODO inject
    private Path path;

    private Map<String, CacheEntry> cache = new HashMap<>();

    @Inject
    @ConfigProperty(name = "cacheFilename", defaultValue = "yr_cache.json")
    private String filename;

    @PostConstruct
    public void createCacheIfNotExisting() {
        path = Paths.get(filename);
        if (!Files.exists(path)) {
            wrapExceptions(() -> Files.createFile(path));
        }
        Optional.ofNullable(wrapExceptions(this::readCache)).ifPresent(this::put);
    }

    private void put(CacheEntry entry) {
        cache.put(entry.getPlace(), entry);
    }

    private void put(String place, LocalDateTime time, Temperature temperature) {
        cache.put(place, new CacheEntry(place, time, temperature));
    }

    private CacheEntry readCache() throws IOException {
        return Files.size(path) == 0
                ? null
                : objectMapper.readValue(path.toFile(), CacheEntry.class);
    }

    private boolean save(String currentLocation, LocalDateTime now, Temperature temperature) throws IOException {
        if (path == null) {
            createCacheIfNotExisting();
        }

        writeToFile(new CacheEntry(currentLocation, now, temperature));
        return true;
    }

    private void writeToFile(CacheEntry cacheEntries) throws IOException {
        Files.write(path, Collections.singletonList(objectMapper.writeValueAsString(cacheEntries)), StandardOpenOption.APPEND);
    }

    @Override
    public Optional<Temperature> get(String currentLocation) {
        if (cache.containsKey(currentLocation)) {
            CacheEntry cacheEntry = cache.get(currentLocation);
            if (isNewlyUpdated(cacheEntry.getTime())) {
                return Optional.of(cacheEntry.getTemperature());
            }
        }
        return Optional.empty();
    }

    private boolean isNewlyUpdated(LocalDateTime cacheEntryUpdateTime) {
        return cacheEntryUpdateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(1)));
    }

    @Override
    public void updateCache(String currentLocation, Temperature temperature) {
        wrapExceptions(() -> save(currentLocation, LocalDateTime.now(), temperature));
        put(currentLocation, LocalDateTime.now(), temperature);
    }

}