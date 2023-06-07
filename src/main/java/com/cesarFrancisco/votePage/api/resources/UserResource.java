package com.cesarFrancisco.votePage.api.resources;

import com.cesarFrancisco.votePage.api.dto.AuthenticationDto;
import com.cesarFrancisco.votePage.api.dto.UserDto;
import com.cesarFrancisco.votePage.api.insertDto.UserInsertDto;
import com.cesarFrancisco.votePage.api.mappers.UserMapper;
import com.cesarFrancisco.votePage.configs.JwtUtils;
import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/users")
@Controller
@RequiredArgsConstructor
public class UserResource {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDto request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = userService.findByEmail(request.email());
        String token = jwtUtils.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList()));
        Map<String, String> map = new HashMap<>();
        map.put("Token", token);
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "test")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("${JwtSecret}");
    }
}
