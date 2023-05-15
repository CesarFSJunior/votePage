package com.cesarFrancisco.votePage.domain.services;

import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.repositories.UserRepository;
import com.cesarFrancisco.votePage.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new ObjectNotFoundException("User not found");
        }

        return user.get();
    }

    public User insert(User user) {


        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public User update(Long id, User user) {
        try {
            User oldUser = userRepository.getReferenceById(id);
            updateUser(oldUser, user);
            return userRepository.save(oldUser);
        }catch (EntityNotFoundException e) {
            throw new ObjectNotFoundException("User not found");
        }
    }

    private void updateUser(User oldUser, User newUser) {
        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
