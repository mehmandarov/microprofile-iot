package no.cx.iot.philipshueapi.hueController.rest.hueAPI;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
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

    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    String executeHTTPGetOnHue(String path) throws IOException {
        return executeHTTPGet(hueURL.getFullURL() + path);
    }

    @Retry
    @CircuitBreaker
    @Fallback(fallbackMethod = "fallback")
    public String executeHTTPGet(String url) throws IOException {
        logger.info("Invoking " + url);
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        InputStream content = httpResponse.getEntity().getContent();
        String responsetext = IOUtils.toString(content);
        httpResponse.close();

        return responsetext;
    }

    private String fallback() {
        return "Could not connect";
    }
}
