package com.george.hubspot.model.dto;

public record OAuthTokenResponseDTO(
        String access_token,
        String token_type,
        Long expires_in,
        String refresh_token
) {}
