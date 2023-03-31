package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vitasoft.vitasofttestbackend.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Autowired
    private UserService userService;



}
