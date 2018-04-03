package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.cx.iot.philipshueapi.hueController.rest.infrastructure.Tuple;

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
        Tuple <Integer, String> responsetext = getCloseableHttpResponse(url);
        ObjectMapper objectMapper = new ObjectMapper();
        if (responsetext.getFirst() == HttpServletResponse.SC_OK) {
            return objectMapper.readValue(responsetext.getSecond(), clazz);
        } else {
            return null;
        }
    }

    @Retry(retryOn = {IOException.class})
    @Timeout(5000)
    @Fallback(fallbackMethod = "fallback")
    private Tuple<Integer, String> getCloseableHttpResponse(String url) throws IOException {
        logger.info("Invoking " + url);
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        InputStream content = response.getEntity().getContent();
        String responsetext = IOUtils.toString(content, charset);
        logger.info("Received " + responsetext + " from " + url + ". With HTTP code: " + statusCode);
        response.close();
        return new Tuple<>(statusCode, responsetext);
    }

    private Tuple<Integer, String> fallback(String url) {
        return new Tuple<>(418, "ERROR: Could not connect to: " + url);
    }
}
