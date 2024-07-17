package ru.polskiy.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.polskiy.conteiners.PostgresTestContainer;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.type.Role;
import ru.polskiy.util.ConnectionManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("user dao implementation test")
public class UserDaoImplTest extends PostgresTestContainer {

    private UserDaoImpl userDao;

    @BeforeEach
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager();
        connectionManager.getConnection(
                container.getJdbcUrl(), container.getUsername(), container.getPassword(),
                "org.postgresql.Driver");

        userDao = new UserDaoImpl(connectionManager);
    }

    @Test
    @DisplayName("find by id method verification test")
    public void testFindById() {
        User user1 = User.builder()
                .login("user1")
                .password("password")
                .role(Role.USER)
                .build();
        user1.onCreate();
        userDao.save(user1);

        Optional<User> foundUser = userDao.findById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("user1", foundUser.get().getLogin());

        Optional<User> notFoundUser = userDao.findById(999L);
        assertFalse(notFoundUser.isPresent());
    }

    @Test
    @DisplayName("find all method verification test")
    public void testFindAll() {
        User user1 = User.builder()
                .login("user1")
                .password("password")
                .role(Role.USER)
                .build();
        user1.onCreate();

        User user2 = User.builder()
                .login("user2")
                .password("password")
                .role(Role.USER)
                .build();
        user2.onCreate();

        userDao.save(user1);
        userDao.save(user2);

        List<User> allUsers = userDao.findAll();
        assertFalse(allUsers.isEmpty());
        assertEquals(3, allUsers.size());
    }

    @Test
    @DisplayName("save method verification test")
    public void testSave() {
        User userToSave = User.builder()
                .login("user1")
                .password("password")
                .role(Role.USER)
                .build();
        userToSave.onCreate();

        User savedUser = userDao.save(userToSave);
        assertNotNull(savedUser.getId());
        assertEquals(userToSave.getLogin(), savedUser.getLogin());
        assertEquals(userToSave.getPassword(), savedUser.getPassword());
        assertEquals(userToSave.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("find by login method verification test")
    public void testFindByLogin() {
        User user = User.builder()
                .login("user1")
                .password("password")
                .role(Role.USER)
                .build();
        user.onCreate();

        userDao.save(user);

        Optional<User> foundUser = userDao.findByLogin("user1");
        assertTrue(foundUser.isPresent());
        assertEquals("user1", foundUser.get().getLogin());

        Optional<User> notFoundUser = userDao.findByLogin("NonExistentLogin");
        assertFalse(notFoundUser.isPresent());
    }

    @Test
    @DisplayName("save null user method verification test")
    public void testSave_NullUser() {
        assertThrows(NullPointerException.class, () -> userDao.save(null));
    }
}
