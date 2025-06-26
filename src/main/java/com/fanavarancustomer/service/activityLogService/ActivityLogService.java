package com.fanavarancustomer.service.activityLogService;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;

import java.util.List;

public interface ActivityLogService {
    ActivityLogDto logAction(ActivityLogDto dto);
    List<ActivityLogDto> getLogsByUser(Long userId);
}