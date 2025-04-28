package com.george.hubspot.controller;

import com.george.hubspot.service.HubspotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private HubspotService hubspotService;

    @GetMapping("/authorize")
    public ResponseEntity<String> generateAuthorizationUrl() {
        String authorizationUrl = hubspotService.buildAuthorizationUrl();
        return ResponseEntity.ok(authorizationUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<String> processCallback(@RequestParam("code") String code) {
        String accessToken = hubspotService.exchangeCodeForToken(code);
        return ResponseEntity.ok(accessToken);
    }
}