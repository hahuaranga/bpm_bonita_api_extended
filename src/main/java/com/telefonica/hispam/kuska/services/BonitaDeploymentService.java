/**
 * 
 */
package com.telefonica.hispam.kuska.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import java.io.File;


/**
 * hahuaranga
 * 10 jun. 2024
 */

@Service
public class BonitaDeploymentService {
	
	@Value("${kuska.bpm.bonita.url}")
	String bonitaApiUrl;
	
	private final RestTemplate restTemplate = new RestTemplate();

    public String deployProcess(String authToken, File barFile) {
    	
        String deployUrl = bonitaApiUrl + "/API/bpm/process";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", authToken);
        headers.add("Content-Type", "multipart/form-data");

        FileSystemResource fileResource = new FileSystemResource(barFile);
        HttpEntity<FileSystemResource> entity = new HttpEntity<>(fileResource, headers);
        ResponseEntity<String> response = restTemplate.exchange(deployUrl, HttpMethod.POST, entity, String.class);
        return response.getBody();
        
    }

}
