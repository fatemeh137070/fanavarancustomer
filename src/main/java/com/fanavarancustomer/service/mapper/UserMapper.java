//package com.fanavarancustomer.service.mapper;
//
//import com.fanavarancustomer.api.dto.user.UserDto;
//import com.fanavarancustomer.dal.entity.Role;
//import com.fanavarancustomer.dal.entity.User;
//import org.mapstruct.*;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//
//    @Mapping(target = "roles", expression = "java(mapRolesToStringSet(user.getRoles()))")
//    UserDto toDto(User user);
//
//    default Set<String> mapRolesToStringSet(Set<Role> roles) {
//        return roles.stream().map(role -> role.getName().name()).collect(Collectors.toSet());
//    }
//}