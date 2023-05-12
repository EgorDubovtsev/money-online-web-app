package com.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    @GetMapping("/index")
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

}
