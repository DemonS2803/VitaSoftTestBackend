package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.vitasofttestbackend.dto.AuthRequestDTO;
import ru.vitasoft.vitasofttestbackend.dto.ResponceDTO;
import ru.vitasoft.vitasofttestbackend.dto.UserDTO;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;
import ru.vitasoft.vitasofttestbackend.enums.Role;
import ru.vitasoft.vitasofttestbackend.security.JwtUtils;
import ru.vitasoft.vitasofttestbackend.services.UserService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/")
    public ResponseEntity<?> login() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println("encoded tet is " + passwordEncoder.encode("test"));
        System.out.println("encoded tet is " + userService.toSha1("test"));
        return new ResponseEntity<>(new ResponceDTO("Hello World", new UserDTO()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Validated AuthRequestDTO loginRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        System.out.println(loginRequest);

        UserEntity user = userService.getUserByLogin(loginRequest.getLogin());

        Authentication authentication = authenticateUser(loginRequest.getLogin(), loginRequest.getPassword());

        String jwt = jwtUtils.generateJwtToken(loginRequest.getLogin(), user.getRole().name());
        System.out.println("token " + jwt);

        System.out.println("data from SCH: " + SecurityContextHolder.getContext().getAuthentication().getName());
        var userdto = UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .token(jwt)
                .role(user.getRole())
                .build();
        var responsedto = new ResponceDTO("success login", userdto);

        return new ResponseEntity<>(responsedto, HttpStatus.ACCEPTED);
    }

    public Authentication authenticateUser(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(login + " " + password);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, userService.toSha1(password)));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }



}
