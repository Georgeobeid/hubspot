package com.george.hubspot.service;

import com.george.hubspot.exception.*;
import com.george.hubspot.model.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
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
            throw new OAuthConfigurationException("Parâmetros obrigatórios de configuração OAuth ausentes");
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


        try {
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
            } else {
                throw new OAuthAuthenticationException("Access token não encontrado na resposta.");
            }
        } catch (RestClientException e) {
            throw new RestClientException("Erro de comunicação com o servidor: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao obter access token: " + e.getMessage());
        }
    }

    public void createContact(ContactDTO contactData) {
        if (accessToken == null) {
            throw new OAuthAuthenticationException("Access Token não encontrado. Realize o OAuth primeiro.");
        }

        if (contactData == null || contactData.properties() == null) {
            throw new MissingRequiredParameterException("Parâmetros obrigatórios do contato estão ausentes.");
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ContactDTO> request = new HttpEntity<>(contactData, headers);

        try {
            restTemplate.postForEntity(
                    "https://api.hubapi.com/crm/v3/objects/contacts",
                    request,
                    String.class
            );
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new TooManyRequestsException("Rate limit excedido. Tente novamente depois.");
        } catch (HttpClientErrorException e) {
            throw new ContactCreationException("Erro ao criar contato: " + e.getMessage());
        } catch (RestClientException e) {
            throw new RestClientException("Erro de comunicação com o servidor HubSpot: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar contato: " + e.getMessage());
        }
    }

    public void processWebhookEvent(Map<String, Object> webhookEvent) {
        System.out.println("Webhook recebido: " + webhookEvent);
    }


}
