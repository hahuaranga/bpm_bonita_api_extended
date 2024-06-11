/**
 * 
 */
package com.telefonica.hispam.kuska.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * hahuaranga
 * 10 jun. 2024
 */

@Service
public class BonitaAuthService {

	@Value("${kuska.bpm.bonita.url}")
	String bonitaApiUrl;
	
    private final RestTemplate restTemplate = new RestTemplate();

    public String authenticate(String username, String password) {
    	
        String loginUrl = bonitaApiUrl + "/loginservice";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        String body = String.format("username=%s&password=%s&redirect=false", username, password);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();
        return responseHeaders.getFirst("Set-Cookie");
        
    }

}
