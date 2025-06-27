package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.controller.UserController;
import com.fanavarancustomer.api.dto.user.RegisterRequest;
import com.fanavarancustomer.api.dto.user.UserDto;
import com.fanavarancustomer.service.userManager.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class UserFacade {


    private final UserManager userManager;

    public UserFacade(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserDto register(RegisterRequest request) {
        UserDto userDto = UserDto.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .build();

        return userManager.register(userDto, request.getPassword(), request.getRoles());
    }

    public UserDto getByUsername(String username) {
        return userManager.getByUsername(username);
    }

    public List<UserDto> getAll() {
        return userManager.getAll();
    }
}