package ru.vitasoft.vitasofttestbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vitasoft.vitasofttestbackend.entities.StatementEntity;
import ru.vitasoft.vitasofttestbackend.enums.StatementStatus;
import ru.vitasoft.vitasofttestbackend.repositories.StatementRepository;
import ru.vitasoft.vitasofttestbackend.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatementService {

    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public boolean saveNewStatement(Long userId,
                                    String title,
                                    String content) {
        var statement = StatementEntity.builder()
                .title(title)
                .content(content)
                .owner(userRepository.getReferenceById(userId))
                .status(StatementStatus.DRAFT)
                .build();
        try {
            statementRepository.save(statement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatementStatus(Long statementId, StatementStatus newStatus) {
        var statement = statementRepository.getReferenceById(statementId);
        try {
            statement.setStatus(newStatus);
            if (newStatus.equals(StatementStatus.SENT)) statement.setCreated(LocalDateTime.now());
            statementRepository.save(statement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateStatementData(Long statementId, String title, String content) {
        var statement = statementRepository.getReferenceById(statementId);
        try {
            statement.setTitle(title);
            statement.setContent(content);
            statementRepository.save(statement);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<StatementEntity> getStatementsByUserId(Long userId, int offset, int limit, String sortParam, boolean isReverseSort) {
        if (!isReverseSort) {
            return statementRepository.findAllByOwner(userRepository.findUserEntityById(userId), PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortParam)));
        } else {
            return statementRepository.findAllByOwner(userRepository.findUserEntityById(userId), PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortParam)));
        }
    }

    public StatementEntity getStatementById(Long statementId) {
        return statementRepository.findStatementEntityById(statementId);
    }

    public ArrayList<StatementEntity> getStatementEntitiesByStatus(String status, int offset, int limit, String sortParam, boolean isReverseSort) {
        if (isReverseSort) {
            return statementRepository.findStatementEntitiesByStatus(StatementStatus.valueOf(status), PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortParam)));
        } else {
            return statementRepository.findStatementEntitiesByStatus(StatementStatus.valueOf(status), PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortParam)));
        }
    }

    public List<StatementEntity> getStatementEntitiesByStatusAndUserLoginPart(String status, String userLoginPart, int offset, int limit, String sortParam, boolean isReverseSort) {
        ArrayList<StatementEntity> statements = statementRepository.findStatementEntitiesByStatus(StatementStatus.valueOf(status));
        System.out.println(statements.size());
        statements = (ArrayList<StatementEntity>) statements.stream().filter(x -> x.getOwner().getLogin().contains(userLoginPart)).collect(Collectors.toList());
        if (!isReverseSort) {
            Collections.sort(statements, new Comparator<StatementEntity>() {
                @Override
                public int compare(StatementEntity o1, StatementEntity o2) {
                    try {
                        return o1.getCreated().compareTo(o2.getCreated());
                    } catch (Exception e) {
                        return 1;
                    }
                }
            });
        } else {
            Collections.sort(statements, new Comparator<StatementEntity>() {
                @Override
                public int compare(StatementEntity o1, StatementEntity o2) {
                    try {
                        return o1.getCreated().compareTo(o2.getCreated());
                    } catch (Exception e) {
                        return 0;
                    }
                }
            }.reversed());
        }
        System.out.println(statements.size());
        if (statements.size() < 5) {
            return statements;
        }
        if (statements.size() - offset * limit < 5) {
            return statements.subList(offset * limit, statements.size());
        }
        return  statements.subList(offset * limit, offset * limit + 5);

//        if (isReverseSort) {
//            statements = statementRepository.findStatementEntitiesByStatus(StatementStatus.valueOf(status), PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortParam)));
//        } else {
//            statements = statementRepository.findStatementEntitiesByStatus(StatementStatus.valueOf(status), PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, sortParam)));
//        }
//        return (ArrayList<StatementEntity>) statements.stream().filter(x -> x.getOwner().getLogin().contains(userLoginPart)).collect(Collectors.toList());

        }
}
