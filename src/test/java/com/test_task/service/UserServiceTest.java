package com.test_task.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import com.test_task.entity.Role;
import com.test_task.entity.User;
import com.test_task.repository.RoleRepository;
import com.test_task.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  private static final String MOCK_USERNAME = "admin";
  private static final String MOCK_NON_EXISTENT_NAME = "junit_user";
  private static final String MOCK_PASSWORD = "1234567890";
  private static final long MOCK_ID = 77;
  @InjectMocks private UserService userService;
  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;
  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void loadUserByUsername() {
    User user = new User(MOCK_USERNAME, MOCK_PASSWORD);

    when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(user);

    assertDoesNotThrow(() -> userService.loadUserByUsername(MOCK_USERNAME));
  }

  @Test
  void save() {
    User user = new User(MOCK_USERNAME, MOCK_PASSWORD);

    when(userRepository.save(user)).thenReturn(user);
    when(roleRepository.findRoleById(1L)).thenReturn(new Role(1L, "Role 1"));
    when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

    assertDoesNotThrow(() -> userService.save(user));
  }

  @Test
  void deleteById() {
    User user = new User(MOCK_USERNAME, MOCK_PASSWORD);
    user.setId(MOCK_ID);
    doNothing().when(userRepository).deleteById(MOCK_ID);

    userService.deleteById(MOCK_ID);

    assertDoesNotThrow(() -> userService.deleteById(MOCK_ID));
  }

  @Test
  void deleteByIdWithException() {
    doThrow(UsernameNotFoundException.class).when(userRepository).deleteById(MOCK_ID);

    assertThrows(UsernameNotFoundException.class, () -> userService.deleteById(MOCK_ID));
  }

  @Test
  void edit() {
    User user = new User(MOCK_USERNAME, MOCK_PASSWORD);

    when(userRepository.save(user)).thenReturn(user);
    when(roleRepository.findRoleById(1L)).thenReturn(new Role(1L, "Role 1"));
    when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());

    assertDoesNotThrow(() -> userService.edit(user));
  }

  @Test
  void findAll() {
    User user = new User(MOCK_USERNAME, MOCK_PASSWORD);
    List<User> users = new ArrayList<>();
    users.add(user);
    when(userRepository.findAll()).thenReturn(users);

    List<User> actualUsers = userService.findAll();

    assertEquals(users.size(), actualUsers.size());
  }

  @Test
  void findById() {
    User expectedUser = new User(MOCK_USERNAME, MOCK_PASSWORD);
    expectedUser.setId(MOCK_ID);
    when(userRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedUser));

    User actualUser = userService.findById(MOCK_ID);

    assertEquals(expectedUser.getId(), actualUser.getId());
  }

  @Test
  void findByUsername() {
    User expectedUser = new User(MOCK_USERNAME, MOCK_PASSWORD);
    when(userRepository.findByUsername(MOCK_USERNAME)).thenReturn(expectedUser);

    User actualUser = userService.findByUsername(expectedUser);

    assertEquals(expectedUser.getUsername(), actualUser.getUsername());
  }
}
