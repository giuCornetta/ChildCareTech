package it.polimi.inginf.childcaretech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gite")
public class GiteController {

    @GetMapping
    public String gite(){
        return "gite";
    }
}
