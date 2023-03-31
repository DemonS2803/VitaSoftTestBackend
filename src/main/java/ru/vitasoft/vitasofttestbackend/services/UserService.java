package ru.vitasoft.vitasofttestbackend.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;
import ru.vitasoft.vitasofttestbackend.enums.Role;
import ru.vitasoft.vitasofttestbackend.repositories.UserRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public Page<UserEntity> usersPagination(int offset, int limit) {
        return userRepository.findAll(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id")));
    }

    public boolean updateUserRoleById(Long id, String newRole) {
        try {
            UserEntity user = userRepository.getReferenceById(id);
            user.setRole(Role.valueOf(newRole));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
