package com.kiwiko.jdashboard.usercredentials.service.web;

import com.kiwiko.jdashboard.usercredentials.client.api.dto.UserCredential;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.CreateUserCredentialOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.QueryUserCredentialsOutput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialInput;
import com.kiwiko.jdashboard.usercredentials.client.api.interfaces.parameters.ValidateUserCredentialOutput;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.JdashboardConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.ServiceRequestLock;
import com.kiwiko.jdashboard.usercredentials.service.api.interfaces.UserCredentialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@JdashboardConfigured
@RequestMapping("/user-credentials/service-api")
@ServiceRequestLock
public class UserCredentialServiceApiController {

    @Inject private UserCredentialService userCredentialService;

    @GetMapping("/query")
    public QueryUserCredentialsOutput query(
            @RequestParam(value = "u", required = false) @Nullable Set<Long> userIds,
            @RequestParam(value = "ct", required = false) @Nullable Set<String> credentialTypes,
            @RequestParam(value = "ir", required = false) @Nullable Boolean isRemoved) {
        QueryUserCredentialsInput.Builder builder = QueryUserCredentialsInput.newBuilder()
                .setUserIds(userIds)
                .setIsRemoved(isRemoved);

        if (credentialTypes != null) {
            Set<String> decodedCredentialTypes = credentialTypes.stream()
                    .map(credentialType -> URLDecoder.decode(credentialType, StandardCharsets.UTF_8))
                    .collect(Collectors.toSet());
            builder.setCredentialTypes(decodedCredentialTypes);
        }

        return userCredentialService.query(builder.build());
    }

    @PostMapping("")
    public CreateUserCredentialOutput createUserCredential(@RequestBody CreateUserCredentialInput input) {
        UserCredential userCredential = userCredentialService.create(input);

        CreateUserCredentialOutput output = new CreateUserCredentialOutput();
        output.setUserCredential(userCredential);

        return output;
    }

    @PostMapping("/{id}/validate")
    public ValidateUserCredentialOutput validateUserCredential(
            @PathVariable("id") long userCredentialId,
            @RequestBody ValidateUserCredentialInput input) {
        input.setUserCredentialId(userCredentialId);
        return userCredentialService.validateUserCredential(input);
    }
}
