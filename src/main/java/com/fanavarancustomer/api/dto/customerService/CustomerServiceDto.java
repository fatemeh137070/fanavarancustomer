package com.fanavarancustomer.api.dto.customerService;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerServiceDto {
    private Long id;
    private String serverName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private Long customerId;

}