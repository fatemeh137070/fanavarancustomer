package com.fanavarancustomer.api.dto.ticketResponseDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private Long id;
    private String message;
    private LocalDateTime respondedAt;
    private Long ticketId;
    private Long responderId;

}