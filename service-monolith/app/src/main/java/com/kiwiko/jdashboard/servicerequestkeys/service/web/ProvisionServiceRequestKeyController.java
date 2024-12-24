package com.kiwiko.jdashboard.servicerequestkeys.service.web;

import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyException;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyInput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ProvisionServiceRequestKeyOutput;
import com.kiwiko.jdashboard.servicerequestkeys.client.ServiceCallRequestKeyProvisioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@JdashboardConfigured
@Controller
public class ProvisionServiceRequestKeyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionServiceRequestKeyController.class);

    @Inject private ServiceCallRequestKeyProvisioner serviceCallRequestKeyProvisioner;

    @PostMapping("/developers/service-request-keys/public-api/v1")
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
            LOGGER.error("Error attempting to provision service request key to {}", input.getServiceClientIdentifier(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/developers/internal-api/v1/service-request-keys")
    public ResponseEntity<ProvisionServiceRequestKeyOutput> provisionInternalServiceRequestKey(
            @RequestBody ProvisionServiceRequestKeyRequest requestBody) {
        ProvisionServiceRequestKeyInput input = ProvisionServiceRequestKeyInput.builder()
                .serviceClientIdentifier(requestBody.getServiceClientIdentifier())
                .description(requestBody.getDescription())
                .expirationTime(requestBody.getExpirationTime())
                .build();
        try {
            ProvisionServiceRequestKeyOutput output = serviceCallRequestKeyProvisioner.provisionInternalServiceRequestKey(input);
            return ResponseEntity.ok(output);
        } catch (ProvisionServiceRequestKeyException e) {
            LOGGER.error("Error attempting to provision internal service request key to {}", input.getServiceClientIdentifier(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
