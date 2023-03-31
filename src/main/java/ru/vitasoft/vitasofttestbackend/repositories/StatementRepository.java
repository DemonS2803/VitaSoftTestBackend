package ru.vitasoft.vitasofttestbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitasoft.vitasofttestbackend.entities.StatementEntity;

@Repository
public interface StatementRepository extends JpaRepository<StatementEntity, Long> {

}
