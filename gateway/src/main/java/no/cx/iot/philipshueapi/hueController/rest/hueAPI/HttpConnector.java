package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@ApplicationScoped
public class HttpConnector {

    @Inject
    @SuppressWarnings("unused")
    private HueURL hueURL;

    @Inject
    private Logger logger;

    @Retry
    @Fallback(fallbackMethod = "fallback")
    <T> T executeHTTPGetOnHue(String path, Class<T> clazz) throws IOException {
        return executeHTTPGet(hueURL.getFullURL() + path, clazz);
    }

    @Retry
    @Fallback(fallbackMethod = "fallback")
    // TODO: This one should be replaced with the rest client, but it doesn't seem like that one is included in Wildfly Swarm yet
    public <T> T executeHTTPGet(String url, Class<T> clazz) throws IOException {
        logger.info("Invoking " + url);
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        InputStream content = httpResponse.getEntity().getContent();
        ObjectMapper objectMapper = new ObjectMapper();
        String responsetext = IOUtils.toString(content, "UTF-8");
        T value = objectMapper.readValue(responsetext, clazz);
        httpResponse.close();

        return value;
    }

    private <T> T fallback(String url, Class<T> clazz) {
        return "ERROR: Could not connect to: " + url;
    }
}
