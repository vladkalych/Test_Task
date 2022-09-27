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
class RoleRepositoryTest {
    private final String ROLE = "USER";

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void init(){
        User user = new User();
        user.setUsername("TEST_USER");
        user.setPassword("1234567890");

        Role role = new Role();
        role.setId(1L);
        role.setName(ROLE);
        role.setUsers(Set.of(user));

        roleRepository.save(role);

    }

    @Test
    void findRoleById() {
        Role roleById = roleRepository.findRoleById(1L);
        assertEquals(ROLE, roleById.getName());
    }
}