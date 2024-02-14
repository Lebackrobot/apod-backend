package development.apodbackend.apis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import development.apodbackend.schemas.MailMessageSchema;

@Service
public class ApodApi {
    @Value("${spring.apod.api.key}")
    private String apod_api_key;

    private MailMessageSchema parseJsonToMailMessageSchema(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            String title = jsonNode.get("title").asText();
            String hdUrl = jsonNode.get("hdurl").asText();
            String explanation = jsonNode.get("explanation").asText();
            String date = jsonNode.get("date").asText();

            return new MailMessageSchema(title, hdUrl, explanation, date);
        } 
        
        catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    public MailMessageSchema getAstronomyPicure() {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://api.nasa.gov/planetary/apod?" + apod_api_key;


        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        
        return parseJsonToMailMessageSchema(response.getBody());
    }
}
