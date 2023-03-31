package ru.vitasoft.vitasofttestbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;

import java.util.ArrayList;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    ArrayList<UserEntity> findAll();
}
