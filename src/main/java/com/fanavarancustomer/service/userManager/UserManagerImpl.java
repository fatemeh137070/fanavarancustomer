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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class UserManagerImpl implements UserManager {


    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public UserManagerImpl(UserRepository userRepository,
                           ActivityLogService activityLogService,
                           RoleRepository roleRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.activityLogService = activityLogService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto register(UserDto dto, String rawPassword, List<String> roles) {
        Set<Role> userRoles = roles.stream()
                .map(role -> roleRepository.findByName(RoleName.valueOf(role.toUpperCase()))
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + role)))
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(dto.getUsername())
//                .password(passwordEncoder.encode(rawPassword))
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
                        .timestamp(LocalDateTime.now())
                        .build()
        );

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}