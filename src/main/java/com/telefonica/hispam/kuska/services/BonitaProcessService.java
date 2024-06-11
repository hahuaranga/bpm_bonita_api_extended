/**
 * 
 */
package com.telefonica.hispam.kuska.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import org.json.JSONObject;
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
public class BonitaProcessService {
	
	@Value("${kuska.bpm.bonita.url}")
	String bonitaApiUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();

    public String startProcessInstance(String authToken, String processDefinitionId, Map<String, Object> parameters) {
    	
        String startProcessUrl = bonitaApiUrl + "/API/bpm/process/" + processDefinitionId + "/instantiation";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", authToken);
        headers.add("Content-Type", "application/json");
        
        JSONObject body = new JSONObject();
        body.put("variables", parameters);

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(startProcessUrl, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

}
