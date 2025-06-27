package com.fanavarancustomer.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    private String name;
    private boolean corporate;
    private String nationalId;
    private String companyRegNo;
    private Long userId;
}