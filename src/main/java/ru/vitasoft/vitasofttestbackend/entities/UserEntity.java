package ru.vitasoft.vitasofttestbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vitasoft.vitasofttestbackend.enums.Role;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;

import java.util.Set;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany
    private Set<StatementEntity> statements;

}
