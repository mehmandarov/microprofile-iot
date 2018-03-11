package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class HttpConnector {

    @Inject
    @SuppressWarnings("unused")
    private HueURL hueURL;

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    @SuppressWarnings("unused")
    @Inject
    @ConfigProperty(name = "charset", defaultValue = "UTF-8")
    private String charset;

    <T> T executeHTTPGetOnHue(String path, Class<T> clazz) throws IOException {
        return executeHTTPGet(hueURL.getFullURL() + path, clazz);
    }

    public <T> T executeHTTPGet(String url, Class<T> clazz) throws IOException {
        String responsetext = getCloseableHttpResponse(url);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responsetext, clazz);
    }

    @Retry
    @Fallback(fallbackMethod = "fallback")
    // TODO: This one should be replaced with the rest client, but it doesn't seem like that one is included in Wildfly Swarm yet
    private String getCloseableHttpResponse(String url) throws IOException {
        logger.info("Invoking " + url);
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
        InputStream content = response.getEntity().getContent();
        String responsetext = IOUtils.toString(content, charset);
        logger.info("Received " + responsetext + " from " + url);
        response.close();
        return responsetext;
    }

    private String fallback(String url) {
        return "ERROR: Could not connect to: " + url;
    }
}
