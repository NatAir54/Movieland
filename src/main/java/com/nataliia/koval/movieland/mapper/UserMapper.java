package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.UserDto;
import com.nataliia.koval.movieland.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
