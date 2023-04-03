package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;
import ru.vitasoft.vitasofttestbackend.services.StatementService;
import ru.vitasoft.vitasofttestbackend.services.UserService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    @Autowired
    private UserService userService;
    @Autowired
    private StatementService statementService;

    @GetMapping("/")
    public ResponseEntity<?> operatorsOnInit(@RequestParam(value = "userLoginPart", defaultValue = "") String userLoginPart,
                                             @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                             @RequestParam(value = "sortParam", defaultValue = "created") String sortParam,
                                             @RequestParam(value = "isReverseSort", defaultValue = "false") boolean isReverseSort) {
        System.out.println(userLoginPart);
        var statements = statementService.getStatementEntitiesByStatusAndUserLoginPart("SENT", userLoginPart, offset, limit, sortParam, isReverseSort);
        System.out.println(statements.size());
        statements.stream().map(x -> {
            x.setContent(String.join("-", x.getContent().split("")));
            return x;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(statements, HttpStatus.OK);
    }

    @PostMapping("/handle_statement")
    public ResponseEntity<?> handleStatement(@RequestParam(value = "isAccepted", defaultValue = "false") boolean isAccepted,
                                             @RequestParam(value = "statementId") Long statementId) {
        try {
            if (isAccepted) statementService.updateStatementStatus(statementId, StatementStatus.ACCEPTED);
            else statementService.updateStatementStatus(statementId, StatementStatus.REJECTED);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
