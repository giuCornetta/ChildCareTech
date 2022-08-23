package it.polimi.inginf.childcaretech.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping
    public CsrfToken csrf(CsrfToken token) {
        System.out.println("TOKEN: " + token);
        return token;
    }

}