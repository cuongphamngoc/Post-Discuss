package com.cuongpn.security.oauth2;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OAuth2AttributeMapper {
    private static final RestTemplate restTemplate = new RestTemplate();
    public static UserAttributes mapAttributes(String registrationId, Map<String, Object> attributes,String authorize_token) {
        return switch (registrationId) {
            case "google" -> new UserAttributes(
                    attributes.getOrDefault("name", "").toString(),
                    attributes.getOrDefault("email", "").toString(),
                    attributes.getOrDefault("picture", "").toString(),
                    attributes.getOrDefault("sub", "").toString()
            );
            case "github" ->
            {
                String email = fetchGitHubUserEmail(authorize_token);
                yield  new UserAttributes(
                        attributes.getOrDefault("login", "").toString(),
                        email,
                        attributes.getOrDefault("avatar_url", "").toString(),
                        attributes.getOrDefault("id", "").toString()
                );
            }
            case "facebook" -> {
                String url = fetchFacebookUserPicture(attributes.getOrDefault("id", "").toString(),authorize_token);
                System.out.println(url);
                yield new UserAttributes(
                        attributes.getOrDefault("name", "").toString(),
                        attributes.getOrDefault("email", "").toString(),
                        url,
                        attributes.getOrDefault("id", "").toString()
                );

            }
            default -> throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        };
    }
    private static String fetchGitHubUserEmail(String accessToken) {
        String url = "https://api.github.com/user/emails";
        System.out.println(accessToken);
        HttpEntity<?> entity = createAuthEntity(accessToken);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        List<Map<String, Object>> emails = response.getBody();

        if (emails != null) {
            for (Map<String, Object> email : emails) {
                if (Boolean.TRUE.equals(email.get("primary")) && Boolean.TRUE.equals(email.get("verified"))) {
                    return email.get("email").toString();
                }
            }
        }
        return "";
    }
    private static HttpEntity<?> createAuthEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + accessToken);
        return new HttpEntity<>(headers);
    }
    private static String fetchFacebookUserPicture(String userId, String accessToken) {
        String url = "https://graph.facebook.com/v21.0/" + userId + "/picture?type=large&redirect=false";
        HttpEntity<?> entity = createAuthEntity(accessToken);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        return getString(data, "url");
    }
    private static String getString(Map<String, Object> attributes, String key) {
        return Optional.ofNullable(attributes.get(key))
                .map(Object::toString)
                .orElse("");
    }
}
