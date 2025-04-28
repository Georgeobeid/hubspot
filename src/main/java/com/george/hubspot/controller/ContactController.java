package com.george.hubspot.controller;

import com.george.hubspot.model.dto.ContactDTO;
import com.george.hubspot.model.dto.HubspotWebhookEventDTO;
import com.george.hubspot.service.HubspotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private HubspotService hubspotService;

    @PostMapping
    @Operation(
            summary = "Criar Contato",
            description = "Cria um novo Contato.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Contato criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ContactDTO.class))
                    )
            }
    )
    public ResponseEntity<String> createContact(@RequestBody ContactDTO contactData) {
        hubspotService.createContact(contactData);
        return ResponseEntity.ok("Contato criado com sucesso!");
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody List<HubspotWebhookEventDTO> payload) {
        hubspotService.processWebhookEvent(payload);
        return ResponseEntity.ok("Evento recebido com sucesso!");
    }
}