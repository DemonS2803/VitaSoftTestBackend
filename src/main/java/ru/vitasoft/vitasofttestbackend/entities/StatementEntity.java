package ru.vitasoft.vitasofttestbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "statements")
@AllArgsConstructor
@NoArgsConstructor
public class StatementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private StatementStatus status;
    @ManyToOne
    private UserEntity owner;
    private LocalDateTime created;

}
