package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.user.RegisterRequest;
import com.fanavarancustomer.api.dto.user.UserDto;
import com.fanavarancustomer.api.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@RequiredArgsConstructor
public class UserController {


    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request) {
        UserDto registeredUser = userFacade.register(request);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        UserDto userDto = userFacade.getByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userFacade.getAll();
        return ResponseEntity.ok(users);
    }
}