package development.apodbackend.apis;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckApi {
    private String url = "***********************";
    private int timeout = 10000;

    public HttpStatusCode getHealthCheck() {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(getClientHttpRequestFactory());

            // Get APOD API values
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode();
        }

        private ClientHttpRequestFactory getClientHttpRequestFactory() {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(timeout);
            factory.setReadTimeout(timeout);
            return factory;
        }
}
