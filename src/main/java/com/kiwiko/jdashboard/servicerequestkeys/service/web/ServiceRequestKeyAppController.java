package com.kiwiko.jdashboard.servicerequestkeys.service.web;

import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.UserAuthCheck;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.ServiceRequestKey;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.GetServiceRequestKeysResponse;
import com.kiwiko.jdashboard.servicerequestkeys.service.interfaces.app.ServiceRequestKeyAppService;
import com.kiwiko.jdashboard.webapp.framework.json.deserialization.api.interfaces.CustomRequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@JdashboardConfigured
@RequestMapping("/developers/service-request-keys/app-api")
@UserAuthCheck
public class ServiceRequestKeyAppController {
    @Inject private ServiceRequestKeyAppService serviceRequestKeyAppService;

    @GetMapping("/mine")
    public GetServiceRequestKeysResponse getMyServiceRequestKeys(
            @AuthenticatedUser User currentUser) {
        return serviceRequestKeyAppService.getServiceRequestKeysForUser(currentUser.getId());
    }

    @PostMapping("")
    public ServiceRequestKey createServiceRequestKey(
            @CustomRequestBody(strategy = CreateServiceRequestKeyRequestBodyDeserializer.class) ServiceRequestKey serviceRequestKey,
            @AuthenticatedUser User currentUser) {
        serviceRequestKey.setCreatedByUserId(currentUser.getId());
        return serviceRequestKeyAppService.create(serviceRequestKey);
    }
}
