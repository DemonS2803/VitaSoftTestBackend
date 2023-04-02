package ru.vitasoft.vitasofttestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponceDTO {
    private String responce;
    private UserDTO user;
}
