package com.fanavarancustomer.api.facade;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.service.activityLogService.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@RequiredArgsConstructor
public class ActivityLogFacade {
    private final ActivityLogService service;

    public ActivityLogFacade(ActivityLogService service) {
        this.service = service;
    }

    public ActivityLogDto logAction(ActivityLogDto dto) {
        return service.logAction(dto);
    }

    public List<ActivityLogDto> getLogsByUser(Long userId) {
        return service.getLogsByUser(userId);
    }
}