package com.znaji.oauth2.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

    @GetMapping("/secure")
    public String secure(Authentication authentication) {
        if (authentication instanceof UsernamePasswordAuthenticationToken un) {
            System.out.println(un);
        } else if (authentication instanceof OAuth2AuthenticationToken oa) {
            System.out.println(oa);
        }
        return "secure.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure-api")
    public ResponseEntity<String> secureApi() {
        return ResponseEntity.ok("Hello from /secure api endpoint");
    }
}
