package com.cesarFrancisco.votePage.api.resources;

import com.cesarFrancisco.votePage.api.dto.UserDto;
import com.cesarFrancisco.votePage.api.insertDto.UserInsertDto;
import com.cesarFrancisco.votePage.api.mappers.UserMapper;
import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping(value = "/users")
@Controller
public class UserResource {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<User> users = userService.findAll();

        List<UserDto> usersDto = users.stream().map(user -> mapper.toUserDto(user)).toList();

        return ResponseEntity.ok().body(usersDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User user = userService.findById(id);

        UserDto userDto = mapper.toUserDto(user);

        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@RequestBody UserInsertDto userInsertDto) {

        User user = mapper.toUser(userInsertDto);

        user = userService.insert(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build(user.getId());

        UserDto userDto = mapper.toUserDto(user);

        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update(@RequestBody UserInsertDto userInsertDto, @PathVariable Long id) {

        User user = mapper.toUser(userInsertDto);

        user = userService.update(id, user);

        UserDto userDto = mapper.toUserDto(user);
        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
