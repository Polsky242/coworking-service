package ru.polskiy.dao.impl;

import ru.polskiy.dao.UserDAO;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;

import java.time.LocalDateTime;
import java.util.*;

public class UserDAOimpl implements UserDAO {

    private final Map<Long, User> users =new HashMap<>();

    private Long id =1L;

    public UserDAOimpl() {
        save(
                new User(id,"Admin","12345",Role.ADMIN)
        );

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(users.values()));
    }

    @Override
    public User save(User entity) {
        entity.setId(id++);
        users.put(entity.getId(),entity);
        return users.get(entity.getId());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        for(User user: users.values()){
            if(user.getLogin().equals(login)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) {
        Long userId = user.getId();
        if(users.containsKey(userId)){
            users.put(userId,user);
            return users.get(userId);
        }else {
            throw new IllegalArgumentException("User with id:"+userId+" doesn't exist");
        }
    }
}
