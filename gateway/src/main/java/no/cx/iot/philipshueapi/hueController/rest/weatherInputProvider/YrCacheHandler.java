package no.cx.iot.philipshueapi.hueController.rest.weatherInputProvider;

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
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.cx.iot.philipshueapi.hueController.rest.lights.LightState;

import static no.cx.iot.philipshueapi.hueController.rest.infrastructure.ExceptionWrapper.wrapExceptions;

@ApplicationScoped
public class YrCacheHandler {

    @Inject
    private ObjectMapper mapper;

    private Path path;

    private Map<String, Tuple<LocalDateTime, String>> cache = new HashMap<>();

    @PostConstruct
    public void createCacheIfNotExisting() {
        path = Paths.get("yrcache.json");
        if (!Files.exists(path)) {
            wrapExceptions(() -> Files.createFile(path));
        }
        wrapExceptions(this::readCache).ifPresent(addEntryToCache());
    }

    private Consumer<CacheEntry> addEntryToCache() {
        return entry -> cache.put(entry.getPlace(), new Tuple<>(LocalDateTime.parse(entry.getTime()), entry.getTemperature()));
    }

    private Optional<CacheEntry> readCache() throws IOException {
        if (Files.size(path) == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(mapper.readValue(path.toFile(), CacheEntry.class));
    }

    private boolean save(String currentLocation, LocalDateTime now, String temperature) throws IOException {
        CacheEntry cacheEntries = new CacheEntry(currentLocation, now.toString(), temperature);
        if (path == null) {
            createCacheIfNotExisting();
        }

        Files.write(path, Collections.singletonList(mapper.writeValueAsString(cacheEntries)), StandardOpenOption.APPEND);
        return true;
    }

    Optional<LightState> getFromNewlyUpdatedCache(String currentLocation, Function<String, LightState> toLightState) {
        if (cache.containsKey(currentLocation)) {
            Tuple<LocalDateTime, String> tuple = cache.get(currentLocation);
            if (isNewlyUpdatedSoUseCache(tuple.getFirst())) {
                return Optional.of(toLightState.apply(tuple.getSecond()));
            }
        }
        return Optional.empty();
    }

    private boolean isNewlyUpdatedSoUseCache(LocalDateTime cacheEntryUpdateTime) {
        return cacheEntryUpdateTime.isAfter(LocalDateTime.now().minus(getCachePeriod()));
    }

    private Duration getCachePeriod() {
        return Duration.ofHours(1);
    }

    void updateCache(String currentLocation, String temperature) {
        wrapExceptions(() -> save(currentLocation, LocalDateTime.now(), temperature));
        cache.put(currentLocation, new Tuple<>(LocalDateTime.now(), temperature));
    }

}
