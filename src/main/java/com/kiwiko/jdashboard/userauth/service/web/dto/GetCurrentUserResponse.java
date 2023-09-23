package com.kiwiko.jdashboard.userauth.service.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GetCurrentUserResponse {
    private long userId;
    private String username;
    private Set<String> permissions;
}
