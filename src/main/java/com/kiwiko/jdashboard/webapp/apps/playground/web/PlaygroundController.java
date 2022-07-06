package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.clients.permissions.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.clients.users.api.dto.User;
import com.kiwiko.jdashboard.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.tools.apiclient.api.dto.ClientResponse;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.auth.AuthenticatedUser;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.jdashboard.framework.controllers.api.annotations.checks.InternalServiceCheck;
import com.kiwiko.jdashboard.tools.apiclient.api.interfaces.JdashboardApiClient;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.PushService;
import com.kiwiko.jdashboard.webapp.streaming.pushservice.api.interfaces.parameters.PushToClientParameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@CrossOriginConfigured
@Controller
public class PlaygroundController {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserClient userClient;
    @Inject private PermissionClient permissionClient;
    @Inject private PushService pushService;

//    @GetMapping("/playground-api/test")
//    @PermissionCheck(PermissionNames.ADMIN)
//    public ResponsePayload test() throws Exception {
//        Permission p = new Permission();
//        p.setPermissionName("admin");
//        p.setUserId(3L);
//
//        CreatePermissionInput i = new CreatePermissionInput();
//        i.setPermission(p);
//
//        CreatePermissionOutput o = permissionClient.create(i);
//        return ResponseBuilder.payload(o);
//        GetUsersByQueryResponse response = userClient.getByQuery(GetUsersQuery.newBuilder().setUserIds(Arrays.asList(1L, 2L, 3L)).build());;
//        return ResponseBuilder.payload(response);
//    }

//    @UserPermissionCheck(PermissionNames.ADMIN)
//    @UserAuthCheck
    @GetMapping("/playground-api/test")
    @ResponseBody
    public ClientResponse<Object> test(@AuthenticatedUser(required = false) User currentUser) throws Exception {
        PushToClientParameters parameters = new PushToClientParameters();
        parameters.setUserId(99L);
        parameters.setRecipientUserId(3L);
        parameters.setServiceId("jdashboard-notifications");
        parameters.setData("hello!");

        pushService.pushToClient(parameters);

//        TestPostApiRequest request = new TestPostApiRequest(currentUser);
        RandomApiRequest request = new RandomApiRequest();
        return jdashboardApiClient.synchronousCall(request);
    }

    @InternalServiceCheck
    @PostMapping("/playground-api/test")
    @ResponseBody
    public String testPost(@RequestBody User user) throws Exception {
        return "Success! " + user.toString();
    }
}
