package ru.gelman.user_crud_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gelman.user_crud_service.dto.UserDto;
import ru.gelman.user_crud_service.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
