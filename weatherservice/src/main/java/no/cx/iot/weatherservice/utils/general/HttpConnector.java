package no.cx.iot.weatherservice.utils.general;

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
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;

@ApplicationScoped
public class HttpConnector {

    @Inject
    private Logger logger;

    @Inject
    @ConfigProperty(name = "charset", defaultValue = "UTF-8")
    private String encoding;

    @Retry(maxRetries = 2)
    @CircuitBreaker
    public String executeHTTPGet(String url) throws IOException {
        logger.info(String.format("Invoking %s", url));
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        InputStream content = httpResponse.getEntity().getContent();
        String responseText = IOUtils.toString(content, encoding);
        httpResponse.close();
        return responseText;
    }
}
