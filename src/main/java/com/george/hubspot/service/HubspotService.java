package com.george.hubspot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class HubspotService {

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    @Value("${hubspot.scope}")
    private String scope;

    @Value("${hubspot.authorization-uri}")
    private String authorizationUri;

    @Value("${hubspot.token-uri}")
    private String tokenUri;

    private String accessToken;

    public String buildAuthorizationUrl() {
        if (authorizationUri == null || clientId == null || redirectUri == null || scope == null) {
            throw new IllegalArgumentException("Missing required OAuth configuration parameters");
        }

        return UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("response_type", "code")
                .build()
                .encode()
                .toUriString();
    }

    public String exchangeCodeForToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                request,
                Map.class
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("access_token")) {
            this.accessToken = (String) responseBody.get("access_token");
            return this.accessToken;
        }

        throw new RuntimeException("Erro ao obter access token");
    }

    public void createContact(Map<String, Object> contactData) {
        if (accessToken == null) {
            throw new RuntimeException("Access Token não encontrado. Realize o OAuth primeiro.");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(contactData, headers);

        try {
            restTemplate.postForEntity(
                    "https://api.hubapi.com/crm/v3/objects/contacts",
                    request,
                    String.class
            );
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new RuntimeException("Rate limit excedido. Tente novamente depois.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar contato: " + e.getMessage());
        }
    }

    public void processWebhookEvent(Map<String, Object> webhookEvent) {
        System.out.println("Webhook recebido: " + webhookEvent);
    }


}
