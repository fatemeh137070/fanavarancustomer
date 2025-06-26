//package com.fanavarancustomer.service.mapper;
//
//import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
//import com.fanavarancustomer.dal.entity.ActivityLog;
//import org.mapstruct.*;
//
//@Mapper(componentModel = "spring")
//public interface ActivityLogMapper {
//    @Mapping(source = "user.id", target = "userId")
//    ActivityLogDto toDto(ActivityLog entity);
//
//    @Mapping(source = "userId", target = "user.id")
//    ActivityLog toEntity(ActivityLogDto dto);
//}