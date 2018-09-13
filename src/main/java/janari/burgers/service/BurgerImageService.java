package janari.burgers.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BurgerImageService {

    private static final String serviceUrl = "https://pplkdijj76.execute-api.eu-west-1.amazonaws.com/prod/recognize";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Question burger gods whether there's a image with a burger on it
     *
     * @param urls image urls to test
     * @return urls that have a burger on them
     */
    public List<String> questionBurgerGods(List<String> urls) {
        if (urls.isEmpty())
            return Collections.emptyList();

        List<String> testUrls = new ArrayList<>(urls);
        List<String> burgers = new ArrayList<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Gson gson = new Gson();

        // Loop until none of the images in testUrls list has a burger in it
        // This way we can get list of images that have burger on it instead of just first one
        while (!testUrls.isEmpty()) {
            HttpEntity<String> entity = new HttpEntity<>(gson.toJson(new BurgerRequest(testUrls)), headers);
            try {
                ResponseEntity<BurgerResponse> burgerResponse = restTemplate.postForEntity(serviceUrl, entity, BurgerResponse.class);
                if (burgerResponse.getStatusCode() == HttpStatus.OK && burgerResponse.hasBody()) {
                    String burgerUrl = burgerResponse.getBody().urlWithBurger;
                    burgers.add(burgerUrl);
                    testUrls.remove(burgerUrl);
                } else {
                    break;
                }
            } catch (Exception ignored) {
                break;
            }
        }
        return burgers;
    }

    @AllArgsConstructor
    private static class BurgerRequest {
        private List<String> urls;
    }

    @Data
    @NoArgsConstructor
    private static class BurgerResponse {
        private String urlWithBurger;
    }

}
