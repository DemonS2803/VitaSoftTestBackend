package ru.vitasoft.vitasofttestbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vitasoft.vitasofttestbackend.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String token;
    private Role role;
}
