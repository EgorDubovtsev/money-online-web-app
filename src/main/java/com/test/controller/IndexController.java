package com.test.controller;


import com.test.entity.UserEntity;
import com.test.service.UsersService;
import com.test.service.entity.Errors;
import lombok.RequiredArgsConstructor;
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
    public String login(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "invalid login or password!");
        }

        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "invalid login or password!");
        }

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
