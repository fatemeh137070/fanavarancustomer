package com.fanavarancustomer.api.dto.role;

import com.fanavarancustomer.service.eum.RoleName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private RoleName name;

}