package com.qma.gateway.controller;

import com.qma.gateway.client.AuthServiceClient;
import com.qma.gateway.dto.LoginDTO;
import com.qma.gateway.dto.RegisterDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final AuthServiceClient authServiceClient;

    public UserController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto) {
        return authServiceClient.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {
        return authServiceClient.login(dto);
    }
}