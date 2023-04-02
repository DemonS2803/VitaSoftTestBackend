package ru.vitasoft.vitasofttestbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.vitasoft.vitasofttestbackend.dto.CreateStatementDTO;
import ru.vitasoft.vitasofttestbackend.dto.UpdateStatementDTO;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;
import ru.vitasoft.vitasofttestbackend.services.StatementService;
import ru.vitasoft.vitasofttestbackend.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StatementService statementService;

    @GetMapping("/")
    public ResponseEntity<?> statementsOnInit(@RequestParam(value = "userId", defaultValue = "4") Long userId,
                                               @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                               @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                               @RequestParam(value = "sortParam", defaultValue = "id") String sortParam,
                                               @RequestParam(value = "isReverseSort", defaultValue = "false") boolean isReverseSort) {
        System.out.println(userId);
        var statements = statementService.getStatementsByUserId(userId, offset, limit, sortParam, isReverseSort);
        return new ResponseEntity<>(statements, HttpStatus.OK);
    }

//    @GetMapping("/statement")
//    public ResponseEntity<?> getStatementData(@RequestParam(value = "statementId") Long statementId) {
//        return new ResponseEntity<>(statementService.getStatementById(statementId), HttpStatus.OK);
//    }

    @PostMapping("/create_statement")
    public ResponseEntity<?> createNewStatement(@RequestParam(value = "userId") Long userId, @RequestBody @Validated CreateStatementDTO dto) {
        System.out.println(userId + " " + dto.toString());
        if (statementService.saveNewStatement(userId, dto.getTitle(), dto.getContent())) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
    @PostMapping("/send_statement")
    public ResponseEntity<?> sendStatementToOperator(@RequestParam(value = "statementId") Long statementId) {
        if (statementService.updateStatementStatus(statementId, StatementStatus.SENT)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/update_statement")
    public ResponseEntity<?> updateStatementStatus(@RequestBody @Validated UpdateStatementDTO dto) {
        if (statementService.updateStatementData(dto.getId(), dto.getTitle(), dto.getContent())) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
