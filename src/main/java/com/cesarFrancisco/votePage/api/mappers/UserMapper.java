package com.cesarFrancisco.votePage.api.mappers;

import com.cesarFrancisco.votePage.api.dto.UserDto;
import com.cesarFrancisco.votePage.api.insertDto.UserInsertDto;
import com.cesarFrancisco.votePage.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    public User toUser(UserInsertDto userInsertDto) {
        return mapper.map(userInsertDto, User.class);
    }

    public UserDto toUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }
}
