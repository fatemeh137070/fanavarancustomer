package com.fanavarancustomer.dal.repository;

import com.fanavarancustomer.dal.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    
    // بازیابی لاگ‌های یک کاربر خاص
    List<ActivityLog> findByUserId(Long userId);
}