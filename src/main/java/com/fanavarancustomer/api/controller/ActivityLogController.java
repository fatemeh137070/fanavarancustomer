package com.fanavarancustomer.api.controller;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.api.facade.ActivityLogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
//@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogFacade facade;

    public ActivityLogController(ActivityLogFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<ActivityLogDto> logAction(@RequestBody ActivityLogDto dto) {
        return ResponseEntity.ok(facade.logAction(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityLogDto>> getLogs(@PathVariable Long userId) {
        return ResponseEntity.ok(facade.getLogsByUser(userId));
    }
}
