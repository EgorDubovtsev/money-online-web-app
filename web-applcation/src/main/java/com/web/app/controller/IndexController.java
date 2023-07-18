package com.web.app.controller;


import com.web.app.entity.UserEntity;
import com.web.app.service.UsersService;
import com.web.app.service.entity.Errors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@Controller
public class IndexController {
    private final UsersService usersService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping(value = "/registration/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registrationProcess(@RequestBody UserEntity user) {
        Errors errors = usersService.createUser(user);
        if (!errors.getErrors().isEmpty()){
            return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("User created.");
    }

}
