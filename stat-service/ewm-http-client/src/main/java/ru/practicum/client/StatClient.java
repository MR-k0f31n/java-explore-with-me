package ru.practicum.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.EndpointHitDto;

import java.util.List;
import java.util.Map;

/**
 * @author MR.k0F31n
 */
public class StatClient {
    private final RestTemplate rest;
    private final String serverUrl;

    public StatClient(@Value("${stats-server.url}") String serverUrl) {
        this.rest = new RestTemplate();
        this.serverUrl = serverUrl;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        rest.setRequestFactory(requestFactory);
    }

    public ResponseEntity<Object> saveHit(EndpointHitDto input) {
        ResponseEntity<Object> response;
        try {
            response = rest.postForEntity(serverUrl + "/hit", input, Object.class);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsByteArray());
        }
        ResponseEntity.BodyBuilder responseBuild = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuild.body(response.getBody());
        }

        return responseBuild.build();
    }

    public ResponseEntity<Object> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        ResponseEntity<Object> response;

        try {
            response = rest.getForEntity(serverUrl + "/stats", Object.class, parameters);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsByteArray());
        }

        ResponseEntity.BodyBuilder responseBuild = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuild.body(response.getBody());
        }

        return responseBuild.build();
    }
}
