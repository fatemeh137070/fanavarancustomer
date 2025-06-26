package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.user.RegisterRequest;
import com.fanavarancustomer.api.dto.user.UserDto;
import com.fanavarancustomer.api.facade.UserFacade;
import lombok.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@RequiredArgsConstructor
public class UserController {

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    private final UserFacade userFacade;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest request) {
        UserDto registeredUser = userFacade.register(request);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userFacade.getByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userFacade.getAll());
    }

}