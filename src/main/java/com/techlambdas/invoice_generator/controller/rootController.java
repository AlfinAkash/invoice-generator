package com.techlambdas.invoice_generator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rootController {

    @GetMapping("/")
    public String home() {
        return "Invoice Generator server is running";
    }
}
