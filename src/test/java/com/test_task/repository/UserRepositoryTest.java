package com.test_task.repository;

import com.test_task.entity.Role;
import com.test_task.entity.Shop;
import com.test_task.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init(){
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        User user = new User();
        user.setUsername("TEST_USER");
        user.setPassword("1234567890");
        user.setRoles(Set.of(role));

        role.setUsers(Set.of(user));

        userRepository.save(user);
    }

    @Test
    void findByUsername() {
        final String truePassword = "1234567890";
        User user = userRepository.findByUsername("TEST_USER");
        assertEquals(truePassword, user.getPassword());
    }
}