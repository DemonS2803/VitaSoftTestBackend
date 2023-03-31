package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.vitasofttestbackend.dto.ResponceDTO;
import ru.vitasoft.vitasofttestbackend.dto.UserChangeRoleDTO;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;
import ru.vitasoft.vitasofttestbackend.services.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> usersOnInit(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                         @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        System.out.println("new request. offset: " + offset + " limit: " + limit);
        return new ResponseEntity<>(userService.usersPagination(offset, limit), HttpStatus.OK);
    }

    @PostMapping("/change_role")
    public ResponseEntity<?> changeUserRoleByIdAndRole(@RequestBody @Validated UserChangeRoleDTO userdto) {
        userService.updateUserRoleById(userdto.getId(), userdto.getRole());
        System.out.println(userdto.getRole() + " " + userdto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
