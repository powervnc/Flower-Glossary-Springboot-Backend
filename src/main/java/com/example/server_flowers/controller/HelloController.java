package com.example.server_flowers.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hello")
public class HelloController {
    @GetMapping
    public ResponseEntity<String> getOK() {
        System.out.println("here we are");
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
