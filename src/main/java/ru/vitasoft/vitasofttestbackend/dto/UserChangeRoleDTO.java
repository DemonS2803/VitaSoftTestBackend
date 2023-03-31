package ru.vitasoft.vitasofttestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserChangeRoleDTO {
    private Long id;
    private String role;

}
