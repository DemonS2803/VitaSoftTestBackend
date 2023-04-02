package ru.vitasoft.vitasofttestbackend.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitasoft.vitasofttestbackend.entities.StatementEntity;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;

import java.util.ArrayList;

@Repository
public interface StatementRepository extends JpaRepository<StatementEntity, Long> {
    ArrayList<StatementEntity> findAllByOwner(UserEntity user, PageRequest of);
    StatementEntity findStatementEntityById(Long statementId);
    ArrayList<StatementEntity> findStatementEntitiesByOwnerAndStatus(UserEntity user, StatementStatus status);
    ArrayList<StatementEntity> findStatementEntitiesByStatus(StatementStatus status, PageRequest of);
    ArrayList<StatementEntity> findStatementEntitiesByStatus(StatementStatus status);

//    ArrayList<StatementEntity> findStatementEntitiesByStatusAndOwner_LoginC(StatementStatus status, UserEntity user, PageRequest of);
}
