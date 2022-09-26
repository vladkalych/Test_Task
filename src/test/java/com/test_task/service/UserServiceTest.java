package com.test_task.service;

import com.test_task.entity.User;
import com.test_task.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Test
    void loadUserByUsername() {
        final String nonExistentName = "junit_user";
        final String existentName = "admin";
        final String password = "1234567890";

        User user = new User(existentName, password);
        userService.save(user);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistentName));
        assertDoesNotThrow(() -> userService.loadUserByUsername(existentName));
    }

    @Test
    void save() {
        final String usernameAndPassword = "junit_test_save";

        User user = new User(usernameAndPassword, usernameAndPassword);
        userService.save(user);

        UserDetails userDetails = userService.loadUserByUsername(usernameAndPassword);
        userDetails.getUsername();
        assertEquals(usernameAndPassword, userDetails.getUsername());
    }

    @Test
    void deleteById() {
        UserDetails junit_test = userService.loadUserByUsername("junit_test");
        assertNotNull(junit_test);
        //userService.deleteById();
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("junit_test"));
    }

    @Test
    void edit() {

    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByUsername() {
    }
}