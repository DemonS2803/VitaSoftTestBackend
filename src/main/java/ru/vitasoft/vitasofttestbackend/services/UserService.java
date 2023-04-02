package ru.vitasoft.vitasofttestbackend.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vitasoft.vitasofttestbackend.entities.UserEntity;
import ru.vitasoft.vitasofttestbackend.enums.Role;
import ru.vitasoft.vitasofttestbackend.repositories.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String toSha1(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
        return DatatypeConverter.printHexBinary(msdDigest.digest());
    }

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

    public UserEntity getUserById(Long id) {
        return userRepository.findUserEntityById(id);
    }

    public UserEntity getUserByLogin(String login) {
        return userRepository.findUserEntityByLogin(login);
    }
}
