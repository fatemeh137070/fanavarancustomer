package com.fanavarancustomer;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.dal.entity.ActivityLog;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.ActivityLogRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.service.activityLogService.ActivityLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivityLogServiceImplTest {

    @Mock
    private ActivityLogRepository activityLogRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ActivityLogServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogAction_success() {
        // Arrange
        ActivityLogDto dto = ActivityLogDto.builder()
                .action("LOGIN")
                .description("User logged in")
                .userId(1L)
                .build();

        User user = User.builder().id(1L).build();
        ActivityLog logEntity = new ActivityLog();
        ActivityLog savedEntity = new ActivityLog();
        savedEntity.setAction("LOGIN");
        savedEntity.setDescription("User logged in");
        savedEntity.setTimestamp(LocalDateTime.now());
        savedEntity.setUser(user);

        // Mock repository and mapper
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(dto, ActivityLog.class)).thenReturn(logEntity);
        when(activityLogRepository.save(logEntity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, ActivityLogDto.class)).thenReturn(dto);

        // Act
        ActivityLogDto result = service.logAction(dto);

        // Assert
        assertNotNull(result);
        assertEquals("LOGIN", result.getAction());
        verify(activityLogRepository).save(logEntity);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetLogsByUser() {
        // Arrange
        ActivityLog logEntity = new ActivityLog();
        ActivityLogDto dto = new ActivityLogDto();

        when(activityLogRepository.findByUserId(1L)).thenReturn(Collections.singletonList(logEntity));
        when(modelMapper.map(logEntity, ActivityLogDto.class)).thenReturn(dto);

        // Act
        List<ActivityLogDto> result = service.getLogsByUser(1L);

        // Assert
        assertEquals(1, result.size());
        verify(activityLogRepository).findByUserId(1L);
    }
}
