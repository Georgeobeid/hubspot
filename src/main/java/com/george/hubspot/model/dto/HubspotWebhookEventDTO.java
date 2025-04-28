package com.george.hubspot.model.dto;

public record HubspotWebhookEventDTO(
        Long appId,
        Long eventId,
        Long subscriptionId,
        Long portalId,
        Long occurredAt,
        String subscriptionType,
        Integer attemptNumber,
        Long objectId,
        String changeSource,
        String changeFlag
) {}