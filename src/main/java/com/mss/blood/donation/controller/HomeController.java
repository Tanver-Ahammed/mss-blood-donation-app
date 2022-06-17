package com.mss.blood.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // home page api
    @GetMapping(path = {"/", "/home"})
    public String home() {
        return "home";
    }

    // developer's page api
    @GetMapping(path = "/developer")
    public String developer() {
        return "developer";
    }


}
