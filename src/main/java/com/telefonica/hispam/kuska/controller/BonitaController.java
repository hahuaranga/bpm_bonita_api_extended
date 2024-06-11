/**
 * 
 */
package com.telefonica.hispam.kuska.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.telefonica.hispam.kuska.services.BonitaAuthService;
import com.telefonica.hispam.kuska.services.BonitaDeploymentService;
import com.telefonica.hispam.kuska.services.BonitaProcessService;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * hahuaranga
 * 10 jun. 2024
 */

@RestController
@RequestMapping("/bonita")
public class BonitaController {
	
	@Autowired
    private BonitaAuthService authService;

    @Autowired
    private BonitaDeploymentService deploymentService;

    @Autowired
    private BonitaProcessService processService;
    
    private static final Logger logger = LoggerFactory.getLogger(BonitaController.class);

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
    	
    	logger.debug("Executing login ...");
        return authService.authenticate(username, password);
    }

    @PostMapping("/deploy")
    public String deploy(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String authToken) throws IOException {
        
    	logger.debug("Executing deploy ...");
    	File tempFile = File.createTempFile("process", ".bar");
        file.transferTo(tempFile);
        return deploymentService.deployProcess(authToken, tempFile);
    }

    @PostMapping("/startProcess")
    public String startProcess(@RequestParam("processDefinitionId") String processDefinitionId, @RequestHeader("Authorization") String authToken, @RequestBody Map<String, Object> parameters) {
        
    	logger.debug("Executing startProcess ...");
		return processService.startProcessInstance(authToken, processDefinitionId, parameters);
    }

}
