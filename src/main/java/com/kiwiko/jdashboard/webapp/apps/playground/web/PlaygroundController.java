package com.kiwiko.jdashboard.webapp.apps.playground.web;

import com.kiwiko.jdashboard.webapp.clients.permissions.api.interfaces.PermissionClient;
import com.kiwiko.jdashboard.webapp.clients.users.api.interfaces.UserClient;
import com.kiwiko.jdashboard.webapp.framework.json.api.ResponseBuilder;
import com.kiwiko.jdashboard.webapp.framework.json.data.ResponsePayload;
import com.kiwiko.jdashboard.webapp.framework.security.authentication.api.annotations.CrossOriginConfigured;
import com.kiwiko.jdashboard.webapp.permissions.core.api.interfaces.PermissionNames;
import com.kiwiko.jdashboard.webapp.permissions.framework.api.annotations.PermissionRequired;
import com.kiwiko.jdashboard.webapp.http.client.api.interfaces.JdashboardApiClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;

@CrossOriginConfigured
@Controller
public class PlaygroundController {

    @Inject private JdashboardApiClient jdashboardApiClient;
    @Inject private UserClient userClient;
    @Inject private PermissionClient permissionClient;

    @GetMapping("/playground-api/test")
    @PermissionRequired(PermissionNames.ADMIN)
    public ResponsePayload test() throws Exception {
//        Permission p = new Permission();
//        p.setPermissionName("admin");
//        p.setUserId(3L);
//
//        CreatePermissionInput i = new CreatePermissionInput();
//        i.setPermission(p);
//
//        CreatePermissionOutput o = permissionClient.create(i);
//        return ResponseBuilder.payload(o);

        return ResponseBuilder.payload(true);

//        GetUsersByQueryResponse response = userClient.getByQuery(GetUsersQuery.newBuilder().setUserIds(Arrays.asList(1L, 2L, 3L)).build());;
//        return ResponseBuilder.payload(response);
    }

//    @GetMapping("/playground-api/test")
//    public ResponsePayload test(@AuthenticatedUser User currentUser) throws Exception {
//        TestPostApiRequest request = new TestPostApiRequest(currentUser);
//        ApiResponse<String> response = jdashboardApiClient.synchronousCall(request);
//
//        return ResponseBuilder.payload(response);
//    }
//
//    @PostMapping("/playground-api/test")
//    @AuthenticationRequired(levels = AuthenticationLevel.INTERNAL_SERVICE)
//    @ResponseBody
//    public String testPost(@RequestBody User user) throws Exception {
//        return "Success! " + user.toString();
//    }
}
