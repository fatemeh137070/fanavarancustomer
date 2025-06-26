package com.fanavarancustomer.api.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private List<String> roles;


}
