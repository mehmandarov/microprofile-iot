package no.cx.iot.gateway;

import javax.enterprise.context.ApplicationScoped;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class HealthChecker implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        String classname = HealthChecker.class.getSimpleName();
        if (canConnect()) {
            return HealthCheckResponse.named(classname)
                    .up()
                    .build();
        }
        else {
            return HealthCheckResponse.named(classname)
                    .withData(classname, "Could not contact gateway")
                    .down()
                    .build();
        }
    }

    private boolean canConnect() {
        try {
            HttpUriRequest request = new HttpGet("http://localhost:9080/hue/verify");
            CloseableHttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
            return httpResponse.getStatusLine().getStatusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
