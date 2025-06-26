package com.fanavarancustomer.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fanavarancustomer.api.dto.activityLogDto.ActivityLogDto;
import com.fanavarancustomer.dal.entity.ActivityLog;


@Configuration
public class ModelMapperConfig {

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//
//        // Mapping Entity → DTO
//        mapper.addMappings(new PropertyMap<ActivityLog, ActivityLogDto>() {
//            @Override
//            protected void configure() {
//                map().setUserId(source.getUser().getId());
//            }
//        });
//
//        // Mapping DTO → Entity
//        mapper.addMappings(new PropertyMap<ActivityLogDto, ActivityLog>() {
//            @Override
//            protected void configure() {
//                skip(destination.getUser()); // ما در Service آن را دستی ست می‌کنیم
//            }
//        });
//
//        return mapper;
//    }

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//
//        mapper.addMappings(new PropertyMap<ActivityLog, ActivityLogDto>() {
//            @Override
//            protected void configure() {
//                // ساده‌تر و بدون لامبدا
//                map().setUserId(source.getUser() != null ? source.getUser().getId() : null);
//            }
//        });
//
//        mapper.addMappings(new PropertyMap<ActivityLogDto, ActivityLog>() {
//            @Override
//            protected void configure() {
//                skip(destination.getUser());
//            }
//        });
//
//        return mapper;
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}