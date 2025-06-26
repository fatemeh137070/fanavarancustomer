package com.fanavarancustomer.service.activityLogService;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.dal.entity.ActivityLog;
import com.fanavarancustomer.dal.entity.User;
import com.fanavarancustomer.dal.repository.ActivityLogRepository;
import com.fanavarancustomer.dal.repository.userRepository.UserRepository;
import com.fanavarancustomer.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ActivityLogDto logAction(ActivityLogDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ActivityLog log = modelMapper.map(dto, ActivityLog.class);
        log.setTimestamp(LocalDateTime.now());
        log.setUser(user);

        ActivityLog saved = activityLogRepository.save(log);
        return modelMapper.map(saved, ActivityLogDto.class);
    }

    @Override
    public List<ActivityLogDto> getLogsByUser(Long userId) {
        return activityLogRepository.findByUserId(userId).stream()
                .map(log -> modelMapper.map(log, ActivityLogDto.class))
                .collect(Collectors.toList());
    }
}