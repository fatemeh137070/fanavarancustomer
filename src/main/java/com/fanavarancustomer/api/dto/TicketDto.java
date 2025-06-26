package com.fanavarancustomer.api.dto;

import com.fanavarancustomer.service.eum.TicketStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {
    private Long id;
    private String subject;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private Long customerId;


}