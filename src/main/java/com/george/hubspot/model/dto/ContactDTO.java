package com.george.hubspot.model.dto;

public record ContactDTO(Properties properties) {

    public static record Properties(
            String firstname,
            String lastname,
            String email
    ) {}
}
