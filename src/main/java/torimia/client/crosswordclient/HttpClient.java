package torimia.client.crosswordclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpClient {

    private static final String ROOT_URI = "http://localhost:8080/";
    private static final String AUTHORIZATION_HEADER = "Bearer ";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> T post(String url, Object objectForRequest, Class<T> responseType) {
        log.info("Request in Json: {}", objectMapper.writeValueAsString(objectForRequest));

        ResponseEntity<T> response = restTemplate.exchange(RequestEntity.post(ROOT_URI + url)
                .body(objectForRequest), responseType);
        log.info("Response {}", response);
        log.info("ResponseBody in Json: {}", objectMapper.writeValueAsString(response.getBody()));

        return response.getBody();
    }

    public <T> T get(String url, String token, Class<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(RequestEntity.get(ROOT_URI + url)
                .headers(getHttpHeadersWithAuth(token)).build(), responseType);
        log.info("Response {}", response);

        return response.getBody();
    }

    private HttpHeaders getHttpHeadersWithAuth(String token) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", AUTHORIZATION_HEADER + token);
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return requestHeaders;
    }
}
