package com.web.app.controller;

import com.google.gson.Gson;

import com.web.app.entity.UserEntity;
import com.web.app.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping(value = "/users")
public class UsersController {
    private final Gson MAPPER = new Gson();
    private final UsersService usersService;

    @GetMapping("/transfer/available")
    public ResponseEntity<String> getAvailableForTransfer(Principal principal) {
        log.debug("GET /transfer/available user={}", principal.getName());

        List<UserEntity> users = usersService.getUsersAvailableForTransfer(principal.getName());

        log.debug("GET /transfer/available RESPONSE: {}", users);
        return ResponseEntity.ok(MAPPER.toJson(users));
    }

    @GetMapping("/current")
    public ResponseEntity<String> getAuthenticatedUserInfo(Principal principal) {
        log.debug("GET /current user={}", principal.getName());

        UserEntity user = usersService.getUserInfoByUsername(principal.getName());

        log.debug("GET /current RESPONSE: {}", user);
        return ResponseEntity.ok(MAPPER.toJson(user));
    }
}
