package com.codffee.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {

    @GetMapping("/api/prueba")
    public String prueba() {
        return "Backend Codffee funcionando correctamente";
    }
}