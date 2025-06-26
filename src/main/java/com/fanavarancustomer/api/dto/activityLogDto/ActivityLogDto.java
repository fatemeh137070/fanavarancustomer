package com.fanavarancustomer.api.dto.activityLogDto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogDto {
    private Long id;
    private String action;
    private String description;
    private LocalDateTime timestamp;
    private Long userId;

}