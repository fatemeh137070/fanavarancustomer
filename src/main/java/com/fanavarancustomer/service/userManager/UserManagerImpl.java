package com.fanavarancustomer.service.userManager;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.api.dto.user.UserDto;
import com.fanavarancustomer.dal.entity.Role;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.role.RoleRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import com.fanavarancustomer.service.activityLogService.ActivityLogService;
import com.fanavarancustomer.service.eum.RoleName;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;


@Service
//@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {


    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserManagerImpl(UserRepository userRepository,
                           ActivityLogService activityLogService,
                           RoleRepository roleRepository,
                           ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    @Override
    public UserDto register(UserDto dto, String rawPassword, List<String> roles) {
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("User must have at least one role");
        }

        Set<Role> userRoles = roles.stream()
                .map(role -> roleRepository.findByName(RoleName.valueOf(role.toUpperCase()))
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + role)))
                .collect(Collectors.toSet());

        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(rawPassword))
                .fullName(dto.getFullName())
                .active(true)
                .roles(userRoles)
                .build();

        User savedUser = userRepository.save(user);

        activityLogService.logAction(
                ActivityLogDto.builder()
                        .userId(savedUser.getId())
                        .action("REGISTER")
                        .description("User registered with username: " + savedUser.getUsername())
                        .timestamp(now)
                        .build()
        );

        // 🔥 دستی ساختن UserDto
        return UserDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .fullName(savedUser.getFullName())
                .roles(savedUser.getRoles().stream()
                        .map(role -> role.getName().name())
                        .collect(Collectors.toSet()))
                .build();
    }
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UserDto dto = modelMapper.map(user, UserDto.class);

        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        dto.setRoles(roleNames);

        return dto;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    UserDto dto = modelMapper.map(user, UserDto.class);
                    Set<String> roleNames = user.getRoles().stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toSet());
                    dto.setRoles(roleNames);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}