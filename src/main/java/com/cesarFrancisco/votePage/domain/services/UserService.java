package com.cesarFrancisco.votePage.domain.services;

import com.cesarFrancisco.votePage.api.dto.AuthenticationDto;
import com.cesarFrancisco.votePage.configs.JwtUtils;
import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.repositories.UserRepository;
import com.cesarFrancisco.votePage.exceptions.NonAuthorizedException;
import com.cesarFrancisco.votePage.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }

        return user.get();
    }

    public User findByEmail(String email) {
        Optional<User> optUser = userRepository.findByEmail(email);

        if (optUser.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }

        return optUser.get();
    }

    public User insert(User user) {

        user.setEmail(user.getEmail().toLowerCase().trim());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        try {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> requestUserOptional = userRepository.findByEmail(email);

            if (requestUserOptional.isEmpty()) {
                throw new ObjectNotFoundException("User not found");
            }

            User requestUser = requestUserOptional.get();

            if (!requestUser.getId().equals(id)) {
                throw new NonAuthorizedException("You can't update another user account");
            }


            User oldUser = userRepository.getReferenceById(id);
            updateUser(oldUser, user);
            return userRepository.save(oldUser);
        } catch (EntityNotFoundException e) {
            throw new ObjectNotFoundException("User not found");
        }
    }

    private void updateUser(User oldUser, User newUser) {

        if (newUser.getName() != null && !newUser.getName().isEmpty() && !newUser.getName().isBlank()) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().isEmpty() && !newUser.getEmail().isBlank()) {
            oldUser.setEmail(newUser.getEmail().toLowerCase().trim());
        }
    }

    public void delete(Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }

        User user = userOptional.get();

        if (!user.getId().equals(id)) {
            throw new NonAuthorizedException("You can't delete another user account");
        }

        userRepository.deleteById(id);
    }

    public String login(AuthenticationDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        } catch (AuthenticationException e) {
            throw new com.cesarFrancisco.votePage.exceptions.AuthenticationException(e.getMessage());
        }
        Optional<User> optUser = userRepository.findByEmail(request.email());

        if (optUser.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }

        User user = optUser.get();

        return jwtUtils.generateToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList()));
    }


}
