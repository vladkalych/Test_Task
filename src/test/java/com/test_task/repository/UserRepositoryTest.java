package com.test_task.repository;

import com.test_task.entity.Role;
import com.test_task.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    private final String USERNAME = "TEST_USER";
    private final String PASSWORD = "1234567890";

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init(){
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setRoles(Set.of(role));

        role.setUsers(Set.of(user));

        userRepository.save(user);
    }

    @Test
    void findByUsername() {
        User user = userRepository.findByUsername(USERNAME);
        assertEquals(PASSWORD, user.getPassword());
    }
}