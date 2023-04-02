package ru.vitasoft.vitasofttestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateStatementDTO {
    private Long id;
    private String title;
    private String content;
    private String Status;
}
