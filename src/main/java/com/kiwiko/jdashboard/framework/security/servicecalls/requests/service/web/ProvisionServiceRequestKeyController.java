package com.kiwiko.jdashboard.framework.security.servicecalls.requests.service.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.framework.security.servicecalls.requests.client.ServiceCallRequestKeyProvisioner;
import com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@JdashboardConfigured
@Controller
@RequestMapping("/developers/service-request-keys/public-api")
public class ProvisionServiceRequestKeyController {

    @Inject private ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner;
    @Inject private Logger logger;

    @PostMapping("/v1")
    public ResponseEntity<ProvisionServiceRequestKeyOutput> provisionServiceRequestKey(
            @RequestBody ProvisionServiceRequestKeyRequest requestBody) {
        ProvisionServiceRequestKeyInput input = ProvisionServiceRequestKeyInput.builder()
                .serviceClientIdentifier(requestBody.getServiceClientIdentifier())
                .description(requestBody.getDescription())
                .expirationTime(requestBody.getExpirationTime())
                .build();
        try {
            ProvisionServiceRequestKeyOutput output = serviceCallRequestKeyProvisioner.provisionExternalServiceRequestKey(input);
            return ResponseEntity.ok(output);
        } catch (ProvisionServiceRequestKeyException e) {
            logger.error("Error attempting to provision service request key to {}", input.getServiceClientIdentifier(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
