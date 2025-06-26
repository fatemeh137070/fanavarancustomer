package com.fanavarancustomer.service.userManager;

import com.fanavarancustomer.api.dto.user.UserDto;

import java.util.List;

public interface UserManager {
    UserDto register(UserDto dto, String rawPassword, List<String> roles);
    UserDto getByUsername(String username);
    List<UserDto> getAll();
}