package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitasoft.vitasofttestbackend.dto.ResponceDTO;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>(new ResponceDTO("Hello World"), HttpStatus.OK);
    }


}
