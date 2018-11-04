package no.cx.iot.gateway.infrastructure;

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
import org.eclipse.microprofile.faulttolerance.Timeout;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class HttpConnector {

    @SuppressWarnings("unused")
    @Inject
    private Logger logger;

    @SuppressWarnings("unused")
    @Inject
    @ConfigProperty(name = "charset", defaultValue = "UTF-8")
    private String charset;

    public <T> T executeHTTPGet(String url, Class<T> clazz) throws IOException {
        Tuple<Integer, String> responsetext = getCloseableHttpResponse(url);
        if (responsetext.getFirst() == 200) {
            return new ObjectMapper().readValue(responsetext.getSecond(), clazz);
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
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Tuple<Integer, String> response = extractResponse(url, httpResponse);
        httpResponse.close();
        return response;
    }

    private Tuple<Integer, String> extractResponse(String url, CloseableHttpResponse response) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        InputStream content = response.getEntity().getContent();
        String responsetext = IOUtils.toString(content, charset);
        logger.info("Received " + responsetext + " from " + url + ". With HTTP code: " + statusCode);
        return new Tuple<>(statusCode, responsetext);
    }

    @SuppressWarnings("unused") // This method is used through reflection by the fallback annotation in the above method
    public Tuple<Integer, String> fallback(String url) throws IOException {
        return new Tuple<>(418, "ERROR: Could not connect to: " + url);
    }
}
