package development.apodbackend.apis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import development.apodbackend.schemas.ApodApiSchema;

@Service
public class ApodApi {
    @Value("${nasa.api.url}")
    private String url;
    private int timeout = 10000;

    
    private ApodApiSchema parseJsonToMailMessageSchema(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String title = jsonNode.get("title").asText();
            String hdUrl = jsonNode.get("hdurl").asText();
            String explanation = jsonNode.get("explanation").asText();
            String date = jsonNode.get("date").asText();

            return new ApodApiSchema(title, hdUrl, explanation, date);
        } 
        
        catch (Exception error) {
            return null;
        }
    }
    
    public ApodApiSchema getAstronomyPicture() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return parseJsonToMailMessageSchema(response.getBody());
        }

        catch (Exception error) {
            return null;
        }
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeout);
        factory.setReadTimeout(timeout);
        return factory;
    }
}
