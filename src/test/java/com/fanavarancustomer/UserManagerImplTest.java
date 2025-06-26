package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.user.UserDto;
import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.role.RoleRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.activityLogService.ActivityLogService;
import com.fanavarancustomer.service.eum.RoleName;
import com.fanavarancustomer.service.userManager.UserManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ActivityLogService activityLogService;

    private ModelMapper modelMapper;

    private UserManagerImpl userManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        userManager = new UserManagerImpl(userRepository, activityLogService, roleRepository, modelMapper);
    }

    @Test
    void testRegisterUser_Success() {
        String username = "javad";
        String password = "1234";
        String fullName = "Javad Gol";
        List<String> roleNames = List.of("CUSTOMER");

        Role role = Role.builder().id(1L).name(RoleName.CUSTOMER).build();

        when(roleRepository.findByName(RoleName.CUSTOMER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(99L);
            return u;
        });

        UserDto dto = UserDto.builder()
                .username(username)
                .fullName(fullName)
                .build();

        UserDto result = userManager.register(dto, password, roleNames);

        assertNotNull(result.getId());
        assertEquals("javad", result.getUsername());
        assertTrue(result.getRoles().contains("CUSTOMER"));

        verify(userRepository, times(1)).save(any(User.class));
        verify(roleRepository, times(1)).findByName(RoleName.CUSTOMER);
    }

    @Test
    void testRegisterUser_RoleNotFound() {
        when(roleRepository.findByName(RoleName.ADMIN)).thenReturn(Optional.empty());

        UserDto dto = UserDto.builder()
                .username("admin")
                .fullName("Admin User")
                .build();

        assertThrows(EntityNotFoundException.class, () ->
                userManager.register(dto, "pass", List.of("ADMIN"))
        );

        verify(roleRepository, times(1)).findByName(RoleName.ADMIN);
    }

    @Test
    void testGetByUsername_Found() {
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .fullName("Test User")
                .roles(Set.of(Role.builder().name(RoleName.SUPPORT).build()))
                .active(true)
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        UserDto result = userManager.getByUsername("testuser");

        assertEquals("testuser", result.getUsername());
        assertTrue(result.getRoles().contains("SUPPORT"));
    }

    @Test
    void testGetByUsername_NotFound() {
        when(userRepository.findByUsername("notexist")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userManager.getByUsername("notexist"));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                User.builder().id(1L).username("u1").fullName("User One")
                        .roles(Set.of(Role.builder().name(RoleName.ADMIN).build())).build(),
                User.builder().id(2L).username("u2").fullName("User Two")
                        .roles(Set.of(Role.builder().name(RoleName.CUSTOMER).build())).build()
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result = userManager.getAll();

        assertEquals(2, result.size());
        assertEquals("u1", result.get(0).getUsername());
        assertTrue(result.get(0).getRoles().contains("ADMIN"));
    }
}