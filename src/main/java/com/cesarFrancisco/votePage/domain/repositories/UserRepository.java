package com.cesarFrancisco.votePage.domain.repositories;

import com.cesarFrancisco.votePage.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

}
